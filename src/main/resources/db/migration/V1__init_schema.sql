SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `menu` (
    `id`             INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `menu_name`      TEXT         CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL,
    `menu_url`       VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL,
    `parent_menu_id` INT          NULL DEFAULT NULL,
    `sort_order`     INT UNSIGNED NULL DEFAULT NULL,
    `usage_status`   TINYINT      NULL DEFAULT '1',
    `is_displayed`   TINYINT      NULL DEFAULT '1',
    `icon`           VARCHAR(20)  CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL,
    `created_by`     INT UNSIGNED NULL DEFAULT NULL,
    `created_at`     TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_by`    INT UNSIGNED NULL DEFAULT NULL,
    `modified_at`    TIMESTAMP    NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `role` (
    `id`          INT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '권한 ID',
    `role_name`   VARCHAR(100)  CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL COMMENT '권한 명',
    `description` VARCHAR(500)  CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL COMMENT '권한 설명',
    `created_by`  INT UNSIGNED  NULL DEFAULT NULL COMMENT '생성자',
    `created_at`  TIMESTAMP     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_by` INT UNSIGNED  NULL DEFAULT NULL COMMENT '수정자',
    `modified_at` TIMESTAMP     NULL DEFAULT NULL COMMENT '수정일',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `menu_role` (
    `id`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `menu_id`    INT UNSIGNED NOT NULL COMMENT '메뉴 아이디',
    `role_id`    INT UNSIGNED NOT NULL COMMENT '권한 아이디',
    `created_by` INT UNSIGNED NULL DEFAULT NULL COMMENT '생성자',
    `created_at` TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    INDEX `role_id` (`role_id` ASC),
    CONSTRAINT `user_menu_ibfk_1`
        FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE,
    CONSTRAINT `user_menu_ibfk_2`
        FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `post` (
    `id`          INT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '게시판 아이디',
    `category_id` VARCHAR(20)   NOT NULL COMMENT '카테고리 아이디',
    `title`       VARCHAR(200)  NOT NULL COMMENT '제목',
    `content`     TEXT          NULL DEFAULT NULL COMMENT '내용',
    `view_count`  INT UNSIGNED  NULL DEFAULT '0' COMMENT '조회수',
    `fixed`       TINYINT       NULL DEFAULT '1' COMMENT '공지사항여부',
    `fixed_at`    TIMESTAMP     NULL DEFAULT NULL COMMENT '공지사항 게시종료시간',
    `created_by`  INT UNSIGNED  NULL DEFAULT NULL COMMENT '생성자',
    `created_at`  TIMESTAMP     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_by` INT UNSIGNED  NULL DEFAULT NULL COMMENT '수정자',
    `modified_at` TIMESTAMP     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `post_comment` (
    `id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '댓글 아이디',
    `content`           TEXT         NULL DEFAULT NULL COMMENT '내용',
    `parent_comment_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '댓글 부모 아이디',
    `post_id`           INT UNSIGNED NOT NULL COMMENT '게시글 아이디',
    `created_by`        INT UNSIGNED NULL DEFAULT NULL COMMENT '생성자',
    `created_at`        TIMESTAMP    NULL DEFAULT NULL COMMENT '생성일',
    PRIMARY KEY (`id`, `post_id`),
    INDEX `fk_post_comment_post_idx` (`post_id` ASC),
    CONSTRAINT `fk_post_comment_post`
        FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `user` (
    `id`          INT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '사용자 아이디',
    `user_name`   VARCHAR(50)   CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL COMMENT '이름',
    `password`    VARCHAR(255)  CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL COMMENT '비밀번호',
    `email`       VARCHAR(100)  CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL COMMENT '이메일',
    `created_by`  INT UNSIGNED  NULL DEFAULT NULL COMMENT '생성자',
    `created_at`  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modified_by` INT UNSIGNED  NULL DEFAULT NULL COMMENT '수정자',
    `modified_at` DATETIME      NULL DEFAULT NULL COMMENT '수정일',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `user_role` (
    `id`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id`    INT UNSIGNED NOT NULL COMMENT '사용자 아이디',
    `role_id`    INT UNSIGNED NOT NULL COMMENT '권한 아이디',
    `created_by` INT UNSIGNED NULL DEFAULT NULL COMMENT '생성자',
    `created_at` TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    INDEX `role_id` (`role_id` ASC),
    CONSTRAINT `user_role_ibfk_1`
        FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `user_role_ibfk_2`
        FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `menu_api` (
    `id`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `menu_id`    INT UNSIGNED NOT NULL,
    `api_url`    VARCHAR(100) NOT NULL,
    `created_by` INT          NULL,
    `created_at` TIMESTAMP    NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `fk_menu_api_menu1_idx` (`menu_id` ASC),
    CONSTRAINT `fk_menu_api_menu1`
        FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB;


SET FOREIGN_KEY_CHECKS = 1;
