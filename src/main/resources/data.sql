-- --------------------------------------------------------
-- Máy chủ:                      127.0.0.1
-- Phiên bản máy chủ:            10.4.32-MariaDB - mariadb.org binary distribution
-- HĐH máy chủ:                  Win64
-- HeidiSQL Phiên bản:           12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Đang kết xuất đổ cấu trúc cơ sở dữ liệu cho urban
DROP DATABASE IF EXISTS `urban`;
CREATE DATABASE IF NOT EXISTS `urban` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `urban`;

-- Đang kết xuất đổ cấu trúc cho bảng urban.calendar_event
DROP TABLE IF EXISTS `calendar_event`;
CREATE TABLE IF NOT EXISTS `calendar_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` longtext DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Đang kết xuất đổ dữ liệu cho bảng urban.calendar_event: ~0 rows (xấp xỉ)
DELETE FROM `calendar_event`;

-- Đang kết xuất đổ cấu trúc cho bảng urban.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `office` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Đang kết xuất đổ dữ liệu cho bảng urban.users: ~1 rows (xấp xỉ)
DELETE FROM `users`;
INSERT INTO `users` (`id`, `address`, `avatar`, `created_at`, `email`, `full_name`, `gender`, `is_active`, `office`, `phone`, `position`, `updated_at`, `username`) VALUES
	(1, '東京都新宿区西新宿1-1-1', 'https://example.com/avatars/user1.jpg', '2025-01-26 09:31:31.000000', 'tanaka.taro@company.jp', '田中太郎', 'その他', b'1', '東京本社', '090-1234-5678', 'エンジニア', '2025-01-30 09:31:31.000000', 'tanaka_taro');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
