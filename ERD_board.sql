-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema board
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `board`;

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
    `id`             INT UNSIGNED                                                      NOT NULL AUTO_INCREMENT COMMENT '메뉴 ID',
    `menu_name`      TEXT CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci'         NULL DEFAULT NULL COMMENT '메뉴 명',
    `menu_url`       VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL COMMENT '메뉴 URL',
    `parent_menu_id` INT                                                               NULL DEFAULT NULL COMMENT '부모 메뉴 ID',
    `sort_order`     INT UNSIGNED                                                      NULL DEFAULT NULL COMMENT '정렬순서',
    `usage_status`   TINYINT(1)                                                        NULL DEFAULT NULL COMMENT '사용여부',
    `created_by`     INT UNSIGNED                                                      NULL DEFAULT NULL COMMENT '생성자',
    `created_at`     TIMESTAMP                                                         NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_by`    INT UNSIGNED                                                      NULL DEFAULT NULL COMMENT '수정자',
    `modified_at`    TIMESTAMP                                                         NULL DEFAULT NULL COMMENT '수정일',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 50
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
    `view_count`  INT UNSIGNED NULL DEFAULT '0' COMMENT '조회수',
    `fixed`       CHAR(1)      NULL DEFAULT 'N' COMMENT '공지사항여부 (Y: 공지, N: 미공지)',
    `fixed_at`    TIMESTAMP    NULL DEFAULT NULL COMMENT '공지사항 게시종료시간',
    `created_by`  INT UNSIGNED NULL DEFAULT NULL COMMENT '생성자 아이디',
    `created_at`  TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_by` INT UNSIGNED NULL DEFAULT NULL COMMENT '수정자 아이디',
    `modified_at` TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 18
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `board`.`post_comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board`.`post_comment`;

CREATE TABLE IF NOT EXISTS `board`.`post_comment`
(
    `id`                INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `content`           TEXT         NULL DEFAULT NULL,
    `parent_comment_id` INT UNSIGNED NULL DEFAULT NULL,
    `post_id`           INT UNSIGNED NOT NULL,
    `created_by`        INT UNSIGNED NULL DEFAULT NULL,
    `created_at`        TIMESTAMP    NULL DEFAULT NULL,
    PRIMARY KEY (`id`, `post_id`),
    CONSTRAINT `fk_post_comment_post`
        FOREIGN KEY (`post_id`)
            REFERENCES `board`.`post` (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 15
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `fk_post_comment_post_idx` ON `board`.`post_comment` (`post_id` ASC) VISIBLE;


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
-- Table `board`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board`.`user`;

CREATE TABLE IF NOT EXISTS `board`.`user`
(
    `id`          INT UNSIGNED                                                      NOT NULL AUTO_INCREMENT COMMENT '사용자 ID',
    `user_name`   VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci'  NOT NULL COMMENT '이름',
    `password`    VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL COMMENT '비밀번호',
    `email`       VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL COMMENT '이메일',
    `created_by`  INT UNSIGNED                                                      NULL     DEFAULT NULL COMMENT '생성자',
    `created_at`  DATETIME                                                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_by` INT UNSIGNED                                                      NULL     DEFAULT NULL COMMENT '수정자',
    `modified_at` DATETIME                                                          NULL     DEFAULT NULL COMMENT '수정일',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 12
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `email_UNIQUE` ON `board`.`user` (`email` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `board`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `board`.`user_role`;

CREATE TABLE IF NOT EXISTS `board`.`user_role`
(
    `user_id`    INT UNSIGNED NOT NULL COMMENT '사용자 ID',
    `role_id`    INT UNSIGNED NOT NULL COMMENT '권한 ID',
    `created_by` INT UNSIGNED NULL DEFAULT NULL COMMENT '생성자',
    `created_at` TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    PRIMARY KEY (`user_id`, `role_id`),
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

CREATE INDEX `role_id` ON `board`.`user_role` (`role_id` ASC) VISIBLE;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
