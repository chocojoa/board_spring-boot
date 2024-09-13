-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema board
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema board
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `board` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `board` ;

-- -----------------------------------------------------
-- Table `board`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `board`.`user` (
                                              `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '사용자 ID',
                                              `user_name` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL COMMENT '이름',
                                              `password` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL COMMENT '비밀번호',
                                              `email` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL COMMENT '이메일',
                                              `created_by` INT UNSIGNED NULL COMMENT '생성자',
                                              `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
                                              `modified_by` INT UNSIGNED NULL COMMENT '수정자',
                                              `modified_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
                                              PRIMARY KEY (`id`),
                                              UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `board`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `board`.`post` (
                                              `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '게시판 아이디',
                                              `category_id` VARCHAR(20) NOT NULL COMMENT '카테고리 아이디',
                                              `title` VARCHAR(200) NOT NULL COMMENT '제목',
                                              `content` TEXT NULL COMMENT '내용',
                                              `view_count` INT UNSIGNED NULL DEFAULT 0 COMMENT '조회수',
                                              `fixed` CHAR(1) NULL DEFAULT 'N' COMMENT '공지사항여부 (Y: 공지, N: 미공지)',
                                              `fixed_at` TIMESTAMP NULL COMMENT '공지사항 게시종료시간',
                                              `created_by` INT UNSIGNED NULL COMMENT '생성자 아이디',
                                              `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
                                              `modified_by` INT UNSIGNED NULL COMMENT '수정자 아이디',
                                              `modified_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
                                              PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `board`.`post_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `board`.`post_comment` (
                                                      `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                      `content` TEXT NULL,
                                                      `parent_comment_id` INT UNSIGNED NULL,
                                                      `post_id` INT UNSIGNED NOT NULL,
                                                      `created_by` INT UNSIGNED NULL,
                                                      `created_at` TIMESTAMP NULL,
                                                      PRIMARY KEY (`id`, `post_id`),
                                                      INDEX `fk_post_comment_post_idx` (`post_id` ASC) VISIBLE,
                                                      CONSTRAINT `fk_post_comment_post`
                                                          FOREIGN KEY (`post_id`)
                                                              REFERENCES `board`.`post` (`id`)
                                                              ON DELETE NO ACTION
                                                              ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
