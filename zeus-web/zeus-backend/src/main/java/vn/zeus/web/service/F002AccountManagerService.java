package vn.zeus.web.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import vn.zeus.web.domain.dto.request.F002CreateNewAccountRequest;
import vn.zeus.web.domain.dto.request.F002SearchUserRequest;
import vn.zeus.web.domain.dto.request.F002UpdateAccountRequest;
import vn.zeus.web.domain.dto.response.F002SearchUserResponse;
import vn.zeus.web.domain.model.Role;
import vn.zeus.web.domain.model.User;
import vn.zeus.web.exception.SystemException;
import vn.zeus.web.exception.ValidationException;
import vn.zeus.web.framework.http.ResponseData;
import vn.zeus.web.mapper.RoleMapper;
import vn.zeus.web.mapper.UserMapper;
import vn.zeus.web.message.Message.Keys;
import vn.zeus.web.message.MessageHelper;
import vn.zeus.web.util.ArrayUtils;
import vn.zeus.web.util.PageUtils;
import vn.zeus.web.util.StringUtils;

@Service
public class F002AccountManagerService extends BaseService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static String[] EXCEL_HEADERs = { "Id", "Tên đăng nhập", "Họ tên", "Số điện thoại", "Email", "Phân quyền",
			"Trạng thái" };

	/**
	 * Data để khởi tạo màn hình S002-Quản lý tài khoản
	 * 
	 * @return
	 */
	public ResponseData getDataInitScreen() {
		ResponseData responseData = new ResponseData();

		// Lấy danh sách phân quyền của hệ thống
		List<Role> roles = roleMapper.selectList(new Role());

		responseData.addResult("roles", roles);

		return responseData;
	}

	/**
	 * Tìm kiếm tài khoản
	 * 
	 * @param request
	 * @return
	 */
	public ResponseData searchUser(F002SearchUserRequest request) {
		ResponseData responseData = new ResponseData();

		PageUtils.createPage(request);

		if (ArrayUtils.isEmpty(request.getRoleIds())) {
			request.setRoleIds(null);
		}

		Page<F002SearchUserResponse> page = userMapper.f002SearchUser(request);
		responseData.setResult(new PageInfo<>(page));
		return responseData;
	}

	/**
	 * Tải xuống excel
	 * 
	 * @param request
	 * @return
	 */
	public ResponseData downloadExcel(F002SearchUserRequest request) {
		Workbook workbook = null;
		ByteArrayOutputStream out = null;

		if (ArrayUtils.isEmpty(request.getRoleIds())) {
			request.setRoleIds(null);
		}
		Page<F002SearchUserResponse> page = userMapper.f002SearchUser(request);

		try {
			workbook = new XSSFWorkbook();
			out = new ByteArrayOutputStream();

			CellStyle style = workbook.createCellStyle();
			style.setBorderBottom(BorderStyle.THIN);
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderLeft(BorderStyle.THIN);
			style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderRight(BorderStyle.THIN);
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderTop(BorderStyle.THIN);
			style.setTopBorderColor(IndexedColors.BLACK.getIndex());

			Sheet sheet = workbook.createSheet("List user");

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < EXCEL_HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(EXCEL_HEADERs[col]);
				cell.setCellStyle(style);
			}

			int rowIdx = 1;
			for (F002SearchUserResponse item : page) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(item.getId());
				row.getCell(0).setCellStyle(style);
				row.createCell(1).setCellValue(item.getUserName());
				row.getCell(1).setCellStyle(style);
				row.createCell(2).setCellValue(item.getFullName());
				row.getCell(2).setCellStyle(style);
				row.createCell(3).setCellValue(item.getPhone());
				row.getCell(3).setCellStyle(style);
				row.createCell(4).setCellValue(item.getEmail());
				row.getCell(4).setCellStyle(style);
				row.createCell(5).setCellValue(item.getRoleName());
				row.getCell(5).setCellStyle(style);
				row.createCell(6).setCellValue(item.getDelFlag() ? "Đã xóa" : "Đang hoạt động");
				row.getCell(6).setCellStyle(style);
			}

			for (int col = 0; col < EXCEL_HEADERs.length; col++) {
				sheet.autoSizeColumn(col);
			}

			workbook.write(out);

			ResponseData responseData = new ResponseData();
			responseData.addResult("file", out.toByteArray());
			return responseData;

		} catch (IOException e) {
			throw new SystemException(MessageHelper.getMessage(Keys.E0009), new Throwable());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (workbook != null) {
					workbook.close();
				}
			} catch (IOException e) {
				throw new SystemException(MessageHelper.getMessage(Keys.E0009), new Throwable());
			}
		}
	}

	/**
	 * Tạo mới tài khoản
	 * 
	 * @param request
	 */
	@Transactional
	public void createNewAccount(F002CreateNewAccountRequest request) {
		String userName = request.getUserName().trim();
		String fullName = request.getFullName().trim();
		String email = request.getEmail() == null ? null : request.getEmail().trim();
		String phone = request.getPhone() == null ? null : request.getPhone().trim();
		Long roleId = request.getRoleId();
		String password = request.getPassword();
		String repeatPassword = request.getRepeatPassword();

		// Kiểm tra nhập lại mật khẩu
		if (!password.equals(repeatPassword)) {
			throw new ValidationException("repeatPassword", MessageHelper.getMessage(Keys.S0003), new Throwable());
		}

		User user = new User();
		user.setUserName(userName);
		user.setFullName(fullName);
		user.setEmail(email);
		user.setPhone(phone);
		user.setRoleId(roleId);
		user.setDelFlag(false);
		user.setPassword(passwordEncoder.encode(password));
		userMapper.insertOne(user);
	}

	/**
	 * Cập nhật tài khoản
	 */
	@Transactional
	public void updateAccount(F002UpdateAccountRequest request) {
		Long id = request.getId();
		String fullName = StringUtils.isEmpty(request.getFullName()) ? null : request.getFullName().trim();
		String email = StringUtils.isEmpty(request.getEmail()) ? null : request.getEmail().trim();
		String phone = StringUtils.isEmpty(request.getPhone()) ? null : request.getPhone().trim();
		Long roleId = request.getRoleId();
		String password = StringUtils.isEmpty(request.getPassword()) ? null : request.getPassword();
		String repeatPassword = StringUtils.isEmpty(request.getRepeatPassword()) ? null : request.getRepeatPassword();
		Boolean delFlag = request.getDelFlag();

		// Kiểm tra nhập lại mật khẩu
		if (password != null || repeatPassword != null) {
			if (password != null && !password.equals(repeatPassword)) {
				throw new ValidationException("repeatPassword", MessageHelper.getMessage(Keys.S0003), new Throwable());
			}

			if (repeatPassword != null && !repeatPassword.equals(password)) {
				throw new ValidationException("repeatPassword", MessageHelper.getMessage(Keys.S0003), new Throwable());
			}
		}

		User userUpdate = new User();
		userUpdate.setId(id);
		userUpdate.setFullName(fullName);
		userUpdate.setEmail(email);
		userUpdate.setPhone(phone);
		userUpdate.setRoleId(roleId);
		userUpdate.setPassword(password != null ? passwordEncoder.encode(password) : null);
		userUpdate.setDelFlag(delFlag);
		userMapper.update(userUpdate);
	}
}
