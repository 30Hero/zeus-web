package vn.zeus.web.util;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lombok.Data;
import vn.zeus.web.domain.dto.request.PageAbleRequest;

@Data
public class PageUtils implements Serializable {
  private static final long serialVersionUID = 1L;

  private static final int DEFAULT_PAGE = 0;

  private static final int MAX_PAGE_SIZE = 100;

  private long total;

  private int pageNum;

  private int pageSize;

  private List<?> rows;

  private String sorts;

  public PageUtils(Page<?> page) {
    this.total = page.getTotal();
    this.pageNum = page.getPageNum();
    this.pageSize = page.getPageSize();
    this.rows = page.getResult();
  }

  public static void createPage(PageAbleRequest pageAbleRequest) {
    if (Objects.nonNull(pageAbleRequest)) {
      int pageNum = Objects.isNull(pageAbleRequest.getPageNum()) ? DEFAULT_PAGE : pageAbleRequest.getPageNum();
      int pageSize = Objects.isNull(pageAbleRequest.getPageSize()) ? MAX_PAGE_SIZE : pageAbleRequest.getPageSize();

      if (Objects.isNull(pageAbleRequest.getSorts())) {
        PageHelper.startPage(pageNum, pageSize);
      } else {
        PageHelper.startPage(pageNum, pageSize, pageAbleRequest.getSorts());
      }
    }
  }
}
