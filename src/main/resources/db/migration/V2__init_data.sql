START TRANSACTION;
INSERT INTO `menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `is_displayed`, `icon`, `created_by`, `created_at`, `modified_by`, `modified_at`) VALUES (1, '대시보드', '/dashboard', 0, 1, 1, 1, '', 1, '2024-12-03 00:00:00', 1, '2025-01-14 19:31:17');
INSERT INTO `menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `is_displayed`, `icon`, `created_by`, `created_at`, `modified_by`, `modified_at`) VALUES (2, '게시판', '', 0, 2, 1, 1, '', 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `is_displayed`, `icon`, `created_by`, `created_at`, `modified_by`, `modified_at`) VALUES (3, '자유게시판', '/boards/free/posts', 2, 1, 1, 1, '', 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `is_displayed`, `icon`, `created_by`, `created_at`, `modified_by`, `modified_at`) VALUES (4, '관리자', '', 0, 3, 1, 1, '', 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `is_displayed`, `icon`, `created_by`, `created_at`, `modified_by`, `modified_at`) VALUES (5, '사용자관리', '/admin/users', 4, 1, 1, 1, '', 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `is_displayed`, `icon`, `created_by`, `created_at`, `modified_by`, `modified_at`) VALUES (6, '메뉴관리', '/admin/menus', 4, 2, 1, 1, '', 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `is_displayed`, `icon`, `created_by`, `created_at`, `modified_by`, `modified_at`) VALUES (7, '권한관리', '/admin/roles', 4, 3, 1, 1, '', 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
COMMIT;


START TRANSACTION;
INSERT INTO `role` (`id`, `role_name`, `description`, `created_by`, `created_at`, `modified_by`, `modified_at`) VALUES (1, '일반 사용자', '일반 사용자 권한입니다.', 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `role` (`id`, `role_name`, `description`, `created_by`, `created_at`, `modified_by`, `modified_at`) VALUES (2, '관리자', '관리자 권한입니다.', 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
COMMIT;


START TRANSACTION;
INSERT INTO `menu_role` (`id`, `menu_id`, `role_id`, `created_by`, `created_at`) VALUES (1,  1, 1, 1, '2024-12-03 00:00:00');
INSERT INTO `menu_role` (`id`, `menu_id`, `role_id`, `created_by`, `created_at`) VALUES (2,  1, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `menu_role` (`id`, `menu_id`, `role_id`, `created_by`, `created_at`) VALUES (3,  2, 1, 1, '2024-12-03 00:00:00');
INSERT INTO `menu_role` (`id`, `menu_id`, `role_id`, `created_by`, `created_at`) VALUES (4,  2, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `menu_role` (`id`, `menu_id`, `role_id`, `created_by`, `created_at`) VALUES (5,  3, 1, 1, '2024-12-03 00:00:00');
INSERT INTO `menu_role` (`id`, `menu_id`, `role_id`, `created_by`, `created_at`) VALUES (6,  3, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `menu_role` (`id`, `menu_id`, `role_id`, `created_by`, `created_at`) VALUES (7,  4, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `menu_role` (`id`, `menu_id`, `role_id`, `created_by`, `created_at`) VALUES (8,  5, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `menu_role` (`id`, `menu_id`, `role_id`, `created_by`, `created_at`) VALUES (9,  6, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `menu_role` (`id`, `menu_id`, `role_id`, `created_by`, `created_at`) VALUES (10, 7, 2, 1, '2024-12-03 00:00:00');
COMMIT;


START TRANSACTION;
INSERT INTO `menu_api` (`id`, `menu_id`, `api_url`, `created_by`, `created_at`) VALUES (1, 3, '/api/boards/free/posts', 1, '2025-03-28 21:59:33');
INSERT INTO `menu_api` (`id`, `menu_id`, `api_url`, `created_by`, `created_at`) VALUES (2, 5, '/api/admin/users',       1, '2025-03-28 21:59:33');
INSERT INTO `menu_api` (`id`, `menu_id`, `api_url`, `created_by`, `created_at`) VALUES (3, 6, '/api/admin/menus',       1, '2025-03-28 21:59:33');
INSERT INTO `menu_api` (`id`, `menu_id`, `api_url`, `created_by`, `created_at`) VALUES (4, 7, '/api/admin/menuRole',    1, '2025-03-28 21:59:33');
INSERT INTO `menu_api` (`id`, `menu_id`, `api_url`, `created_by`, `created_at`) VALUES (5, 7, '/api/admin/roles',       1, '2025-03-28 21:59:33');
INSERT INTO `menu_api` (`id`, `menu_id`, `api_url`, `created_by`, `created_at`) VALUES (6, 7, '/api/admin/userRole',    1, '2025-03-28 21:59:33');
COMMIT;
