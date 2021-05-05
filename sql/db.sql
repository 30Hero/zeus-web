DROP DATABASE IF EXISTS zeus;
CREATE DATABASE zeus CHARACTER SET 'utf8mb4';

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `del_flag` BOOLEAN DEFAULT false,
  `version` int NOT NULL DEFAULT 1,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  `updated_by` int,
   PRIMARY KEY (`id`),
   UNIQUE KEY `UNI_role_01` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `role` (`id`, `name`, `description`, `del_flag`, `version`, `created_at`, `updated_at`, `updated_by`) VALUES
(1, 'Admin', 'Quản trị tối cao', true, 1, NOW(), NOW(), NULL),
(2, 'Employee', 'Nhân viên', true, 1, NOW(), NOW(), NULL);


DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `screen_id` varchar(255) NOT NULL,
  `screen_name` varchar(255) NOT NULL,
  `function_id` varchar(255) NOT NULL,
  `function_name` varchar(255) NOT NULL,
  `action_id` varchar(255) NOT NULL,
  `action_name` varchar(255) NOT NULL,
  `version` int NOT NULL DEFAULT 1,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  `updated_by` int,
   PRIMARY KEY (`id`),
   UNIQUE KEY `UNI_permission_01` (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `permission` (`id`, `screen_id`, `screen_name`,`function_id`, `function_name`,`action_id`, `action_name`, `version`, `created_at`, `updated_at`, `updated_by`) VALUES
(1, 'S001', 'Menu', 'F001', 'Lấy thông tin người dùng', 'S001_A001', 'Bảng điều khiển', 1, NOW(), NOW(), NULL),
(2, 'S001', 'Menu', 'F001', 'Lấy thông tin người dùng', 'S001_A002', 'Thống kê', 1, NOW(), NOW(), NULL),
(3, 'S001', 'Menu', 'F001', 'Lấy thông tin người dùng', 'S001_A003', 'Quản lý hệ thống', 1, NOW(), NOW(), NULL),
(4, 'S001', 'Menu', 'F001', 'Lấy thông tin người dùng', 'S001_A004', 'Quản lý người dùng', 1, NOW(), NOW(), NULL),
(5, 'S001', 'Menu', 'F001', 'Lấy thông tin người dùng', 'S001_A005', 'Quản lý vai trò', 1, NOW(), NOW(), NULL),
(6, 'S001', 'Menu', 'F001', 'Lấy thông tin người dùng', 'S001_A006', 'Quản lý phân quyền', 1, NOW(), NOW(), NULL),
(7, 'S001', 'Menu', 'F001', 'Lấy thông tin người dùng', 'S001_A007', 'Quản lý log', 1, NOW(), NOW(), NULL),
(8, 'S001', 'Menu', 'F001', 'Lấy thông tin người dùng', 'S001_A008', 'Log thao tác người dùng', 1, NOW(), NOW(), NULL),
(9, 'S001', 'Menu', 'F001', 'Lấy thông tin người dùng', 'S001_A009', 'Log đăng nhập hệ thống', 1, NOW(), NOW(), NULL);

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `role_id` int NOT NULL,
  `permission_id` int NOT NULL,
  `display_operation_control` tinyint(1) NOT NULL DEFAULT 0,
  `version` int NOT NULL DEFAULT 1,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  `updated_by` int,
   PRIMARY KEY (`role_id`, `permission_id`),
   FOREIGN KEY (role_id) REFERENCES role(id),
   FOREIGN KEY (permission_id) REFERENCES permission(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `role_permission` (`role_id`, `permission_id`,`display_operation_control`, `version`, `created_at`, `updated_at`, `updated_by`) VALUES
(1, 1, 2, 1, NOW(), NOW(), NULL),
(1, 2, 2, 1, NOW(), NOW(), NULL),
(1, 3, 2, 1, NOW(), NOW(), NULL),
(1, 4, 2, 1, NOW(), NOW(), NULL),
(1, 5, 2, 1, NOW(), NOW(), NULL),
(1, 6, 2, 1, NOW(), NOW(), NULL),
(1, 7, 2, 1, NOW(), NOW(), NULL),
(1, 8, 2, 1, NOW(), NOW(), NULL),
(1, 9, 2, 1, NOW(), NOW(), NULL),
(2, 1, 2, 1, NOW(), NOW(), NULL),
(2, 2, 2, 1, NOW(), NOW(), NULL),
(2, 3, 2, 1, NOW(), NOW(), NULL),
(2, 4, 2, 1, NOW(), NOW(), NULL),
(2, 5, 2, 1, NOW(), NOW(), NULL),
(2, 6, 2, 1, NOW(), NOW(), NULL),
(2, 7, 2, 1, NOW(), NOW(), NULL),
(2, 8, 2, 1, NOW(), NOW(), NULL),
(2, 9, 2, 1, NOW(), NOW(), NULL);

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(150) NOT NULL,
  `role_id` int NOT NULL,
  `full_name` varchar(150) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `remark` text DEFAULT NULL,
  `login_ip` varchar(128) DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,                 
  `del_flag` BOOLEAN DEFAULT false,
  `version` int NOT NULL DEFAULT 1,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  `updated_by` int,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNI_user_01` (`user_name`),
  FOREIGN KEY (role_id) REFERENCES role(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user` (`full_name`, `phone`,`email`,`address`,`user_name`,`password`,`role_id`,`img`,`remark`,`del_flag`, `version`, `created_at`, `updated_at`, `updated_by`) VALUES
('Administrator', '0374412262', 'hoangbao129799@gmail.com', NULL, 'admin', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 1, NULL, 'Quản trị hệ thống', false, 1, NOW(), NOW(), NULL),
('Employee 1', '0374412262', 'hoangbao129799@gmail.com', 'Đà Nẵng', 'employee1', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 2, NULL, 'Nhân viên', false, 1, NOW(), NOW(), NULL),
('Employee 2', '0374412262', 'hoangbao129799@gmail.com', 'Đà Nẵng', 'employee2', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 2, NULL, 'Nhân viên', false, 1, NOW(), NOW(), NULL),
('Employee 3', '0374412262', 'hoangbao129799@gmail.com', 'Đà Nẵng', 'employee3', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 2, NULL, 'Nhân viên', false, 1, NOW(), NOW(), NULL),
('Employee 4', '0374412262', 'hoangbao129799@gmail.com', 'Đà Nẵng', 'employee4', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 2, NULL, 'Nhân viên', false, 1, NOW(), NOW(), NULL),
('Employee 5', '0374412262', 'hoangbao129799@gmail.com', 'Đà Nẵng', 'employee5', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 2, NULL, 'Nhân viên', false, 1, NOW(), NOW(), NULL),
('Employee 6', '0374412262', 'hoangbao129799@gmail.com', 'Đà Nẵng', 'employee6', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 2, NULL, 'Nhân viên', false, 1, NOW(), NOW(), NULL),
('Employee 7', '0374412262', 'hoangbao129799@gmail.com', 'Đà Nẵng', 'employee7', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 2, NULL, 'Nhân viên', false, 1, NOW(), NOW(), NULL),
('Employee 8', '0374412262', 'hoangbao129799@gmail.com', 'Đà Nẵng', 'employee8', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 2, NULL, 'Nhân viên', false, 1, NOW(), NOW(), NULL),
('Employee 9', '0374412262', 'hoangbao129799@gmail.com', 'Đà Nẵng', 'employee9', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 2, NULL, 'Nhân viên', false, 1, NOW(), NOW(), NULL),
('Employee 10', '0374412262', 'hoangbao129799@gmail.com', 'Đà Nẵng', 'employee10', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 2, NULL, 'Nhân viên', false, 1, NOW(), NOW(), NULL),
('Employee 11', '0374412262', 'hoangbao129799@gmail.com', 'Đà Nẵng', 'employee11', '$2a$10$huIGK4kQmQJNBFOdAv/YUO9mApTfqjcNppBp3TZvua/5giJHhdei2', 2, NULL, 'Nhân viên', false, 1, NOW(), NOW(), NULL);




