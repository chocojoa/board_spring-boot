-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema board
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `board` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `board`;

-- -----------------------------------------------------
-- Table `board`.`menu`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board`.`menu`;

CREATE TABLE IF NOT EXISTS `board`.`menu`
(
    `id`             INT UNSIGNED                                                      NOT NULL AUTO_INCREMENT COMMENT '메뉴 아이디',
    `menu_name`      TEXT CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci'         NULL DEFAULT NULL COMMENT '메뉴 명',
    `menu_url`       VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL COMMENT '메뉴 URL',
    `parent_menu_id` INT                                                               NULL DEFAULT NULL COMMENT '부모 메뉴 아이디',
    `sort_order`     INT UNSIGNED                                                      NULL DEFAULT NULL COMMENT '정렬순서',
    `usage_status`   TINYINT                                                           NULL DEFAULT 1 COMMENT '사용여부',
    `icon`           VARCHAR(20) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci'  NULL DEFAULT NULL COMMENT '아이콘',
    `created_by`     INT UNSIGNED                                                      NULL DEFAULT NULL COMMENT '생성자',
    `created_at`     TIMESTAMP                                                         NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_by`    INT UNSIGNED                                                      NULL DEFAULT NULL COMMENT '수정자',
    `modified_at`    TIMESTAMP                                                         NULL DEFAULT NULL COMMENT '수정일',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `board`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board`.`role`;

CREATE TABLE IF NOT EXISTS `board`.`role`
(
    `id`          INT UNSIGNED                                                      NOT NULL AUTO_INCREMENT COMMENT '권한 ID',
    `role_name`   VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL COMMENT '권한 명',
    `description` VARCHAR(500) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL COMMENT '권한 설명',
    `created_by`  INT UNSIGNED                                                      NULL DEFAULT NULL COMMENT '생성자',
    `created_at`  TIMESTAMP                                                         NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_by` INT UNSIGNED                                                      NULL DEFAULT NULL COMMENT '수정자',
    `modified_at` TIMESTAMP                                                         NULL DEFAULT NULL COMMENT '수정일',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `board`.`menu_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board`.`menu_role`;

CREATE TABLE IF NOT EXISTS `board`.`menu_role`
(
    `menu_id`    INT UNSIGNED NOT NULL COMMENT '메뉴 아이디',
    `role_id`    INT UNSIGNED NOT NULL COMMENT '권한 아이디',
    `created_by` INT UNSIGNED NULL DEFAULT NULL COMMENT '생성자',
    `created_at` TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    PRIMARY KEY (`menu_id`, `role_id`),
    INDEX `role_id` (`role_id` ASC) VISIBLE,
    CONSTRAINT `user_menu_ibfk_1`
        FOREIGN KEY (`menu_id`)
            REFERENCES `board`.`menu` (`id`),
    CONSTRAINT `user_menu_ibfk_2`
        FOREIGN KEY (`role_id`)
            REFERENCES `board`.`role` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `board`.`post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board`.`post`;

CREATE TABLE IF NOT EXISTS `board`.`post`
(
    `id`          INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '게시판 아이디',
    `category_id` VARCHAR(20)  NOT NULL COMMENT '카테고리 아이디',
    `title`       VARCHAR(200) NOT NULL COMMENT '제목',
    `content`     TEXT         NULL DEFAULT NULL COMMENT '내용',
    `view_count`  INT UNSIGNED NULL DEFAULT 0 COMMENT '조회수',
    `fixed`       TINYINT      NULL DEFAULT 1 COMMENT '공지사항여부\n',
    `fixed_at`    TIMESTAMP    NULL DEFAULT NULL COMMENT '공지사항 게시종료시간',
    `created_by`  INT UNSIGNED NULL DEFAULT NULL COMMENT '생성자',
    `created_at`  TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_by` INT UNSIGNED NULL DEFAULT NULL COMMENT '수정자',
    `modified_at` TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `board`.`post_comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board`.`post_comment`;

CREATE TABLE IF NOT EXISTS `board`.`post_comment`
(
    `id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '댓글 아이디',
    `content`           TEXT         NULL DEFAULT NULL COMMENT '내용',
    `parent_comment_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '댓글 부모 아이디',
    `post_id`           INT UNSIGNED NOT NULL COMMENT '게시글 아이디',
    `created_by`        INT UNSIGNED NULL DEFAULT NULL COMMENT '생성자',
    `created_at`        TIMESTAMP    NULL DEFAULT NULL COMMENT '생성일',
    PRIMARY KEY (`id`, `post_id`),
    INDEX `fk_post_comment_post_idx` (`post_id` ASC) VISIBLE,
    CONSTRAINT `fk_post_comment_post`
        FOREIGN KEY (`post_id`)
            REFERENCES `board`.`post` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `board`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board`.`user`;

CREATE TABLE IF NOT EXISTS `board`.`user`
(
    `id`          INT UNSIGNED                                                      NOT NULL AUTO_INCREMENT COMMENT '사용자 아이디\n',
    `user_name`   VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci'  NOT NULL COMMENT '이름',
    `password`    VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL COMMENT '비밀번호',
    `email`       VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL COMMENT '이메일',
    `created_by`  INT UNSIGNED                                                      NULL     DEFAULT NULL COMMENT '생성자',
    `created_at`  DATETIME                                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_by` INT UNSIGNED                                                      NULL     DEFAULT NULL COMMENT '수정자',
    `modified_at` DATETIME                                                          NULL     DEFAULT NULL COMMENT '수정일',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `board`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board`.`user_role`;

CREATE TABLE IF NOT EXISTS `board`.`user_role`
(
    `user_id`    INT UNSIGNED NOT NULL COMMENT '사용자 아이디',
    `role_id`    INT UNSIGNED NOT NULL COMMENT '권한 아이디',
    `created_by` INT UNSIGNED NULL DEFAULT NULL COMMENT '생성자',
    `created_at` TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    PRIMARY KEY (`user_id`, `role_id`),
    INDEX `role_id` (`role_id` ASC) VISIBLE,
    CONSTRAINT `user_role_ibfk_1`
        FOREIGN KEY (`user_id`)
            REFERENCES `board`.`user` (`id`),
    CONSTRAINT `user_role_ibfk_2`
        FOREIGN KEY (`role_id`)
            REFERENCES `board`.`role` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `board`.`menu`
-- -----------------------------------------------------
START TRANSACTION;
USE `board`;
INSERT INTO `board`.`menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `icon`,
                            `created_by`, `created_at`, `modified_by`, `modified_at`)
VALUES (1, '대시보드', '/dashboard', 0, 1, 1, NULL, 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `icon`,
                            `created_by`, `created_at`, `modified_by`, `modified_at`)
VALUES (2, '게시판', NULL, 0, 2, 1, NULL, 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `icon`,
                            `created_by`, `created_at`, `modified_by`, `modified_at`)
VALUES (3, '자유게시판', '/boards/free/posts', 2, 1, 1, NULL, 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `icon`,
                            `created_by`, `created_at`, `modified_by`, `modified_at`)
VALUES (4, '관리자', NULL, 0, 3, 1, NULL, 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `icon`,
                            `created_by`, `created_at`, `modified_by`, `modified_at`)
VALUES (5, '사용자관리', '/admin/users', 4, 1, 1, NULL, 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `icon`,
                            `created_by`, `created_at`, `modified_by`, `modified_at`)
VALUES (6, '메뉴관리', '/admin/menus', 4, 2, 1, NULL, 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_order`, `usage_status`, `icon`,
                            `created_by`, `created_at`, `modified_by`, `modified_at`)
VALUES (7, '권한관리', '/admin/roles', 4, 3, 1, NULL, 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `board`.`role`
-- -----------------------------------------------------
START TRANSACTION;
USE `board`;
INSERT INTO `board`.`role` (`id`, `role_name`, `description`, `created_by`, `created_at`, `modified_by`, `modified_at`)
VALUES (1, '일반 사용자', '일반 사용자 권한입니다.', 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`role` (`id`, `role_name`, `description`, `created_by`, `created_at`, `modified_by`, `modified_at`)
VALUES (2, '관리자', '관리자 권한입니다.', 1, '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `board`.`menu_role`
-- -----------------------------------------------------
START TRANSACTION;
USE `board`;
INSERT INTO `board`.`menu_role` (`menu_id`, `role_id`, `created_by`, `created_at`)
VALUES (1, 1, 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu_role` (`menu_id`, `role_id`, `created_by`, `created_at`)
VALUES (2, 1, 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu_role` (`menu_id`, `role_id`, `created_by`, `created_at`)
VALUES (3, 1, 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu_role` (`menu_id`, `role_id`, `created_by`, `created_at`)
VALUES (1, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu_role` (`menu_id`, `role_id`, `created_by`, `created_at`)
VALUES (2, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu_role` (`menu_id`, `role_id`, `created_by`, `created_at`)
VALUES (3, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu_role` (`menu_id`, `role_id`, `created_by`, `created_at`)
VALUES (4, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu_role` (`menu_id`, `role_id`, `created_by`, `created_at`)
VALUES (5, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu_role` (`menu_id`, `role_id`, `created_by`, `created_at`)
VALUES (6, 2, 1, '2024-12-03 00:00:00');
INSERT INTO `board`.`menu_role` (`menu_id`, `role_id`, `created_by`, `created_at`)
VALUES (7, 2, 1, '2024-12-03 00:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `board`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `board`;
INSERT INTO `board`.`user` (`id`, `user_name`, `password`, `email`, `created_by`, `created_at`, `modified_by`,
                            `modified_at`)
VALUES (1, '테스트맨', '$2a$10$euUceLsbNzPxfwK6YF5WdOXY9OF39xD2ZLOix91nfTm79lIbLLKfu', 'pcj1004@gmail.com', 1,
        '2024-12-03 00:00:00', 1, '2024-12-03 00:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `board`.`user_role`
-- -----------------------------------------------------
START TRANSACTION;
USE `board`;
INSERT INTO `board`.`user_role` (`user_id`, `role_id`, `created_by`, `created_at`)
VALUES (1, 2, 1, '2024-12-03 00:00:00');

COMMIT;

