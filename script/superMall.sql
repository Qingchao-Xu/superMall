CREATE DATABASE IF NOT EXISTS `cart`
    DEFAULT CHARACTER SET = 'utf8mb4';

CREATE DATABASE IF NOT EXISTS `order`
    DEFAULT CHARACTER SET = 'utf8mb4';

CREATE DATABASE IF NOT EXISTS `payment`
    DEFAULT CHARACTER SET = 'utf8mb4';

CREATE DATABASE IF NOT EXISTS `product`
    DEFAULT CHARACTER SET = 'utf8mb4';

CREATE DATABASE IF NOT EXISTS `user`
    DEFAULT CHARACTER SET = 'utf8mb4';

USE `user`;

CREATE TABLE IF NOT EXISTS `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `email` varchar(100) NOT NULL,
    `password_hashed` varchar(100) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user` (`username`, `email`, `password_hashed`) VALUES ('testuser', 'testuser@example.com', 'hashedpassword');

USE `product`;

CREATE TABLE IF NOT EXISTS `product` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `description` varchar(255) NOT NULL,
    `picture` varchar(255) NOT NULL,
    `price` decimal(10, 2) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `category` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `description` varchar(255) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `product_category` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `product_id` bigint(20) NOT NULL,
    `category_id` bigint(20) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `product` (`name`, `description`, `picture`, `price`) VALUES ('Test Product', 'This is a test product.', 'test.jpg', 19.99);
INSERT INTO `category` (`name`, `description`) VALUES ('TestCategory', 'This is a test category.');
INSERT INTO `product_category` (`product_id`, `category_id`) VALUES (1, 1);

USE `cart`;

CREATE TABLE IF NOT EXISTS `cart` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL,
    `product_id` bigint(20) NOT NULL,
    `quantity` int(11) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `cart` (`user_id`, `product_id`, `quantity`) VALUES (1, 1, 2);

USE `order`;

CREATE TABLE IF NOT EXISTS `order` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `order_id` bigint(20) NOT NULL UNIQUE,
    `user_id` bigint(20) NOT NULL,
    `user_currency` varchar(10) NOT NULL,
    `email` varchar(255) NOT NULL,
    `street_address` VARCHAR(255) NOT NULL,
    `city` VARCHAR(100) NOT NULL,
    `state` VARCHAR(100) NOT NULL,
    `country` VARCHAR(100) NOT NULL,
    `zip_code` INT NOT NULL,
    `order_state` ENUM('placed', 'paid', 'canceled') NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `order_item` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `product_id` bigint(20) NOT NULL,
    `order_id` bigint(20) NOT NULL,
    `quantity` int(11) NOT NULL,
    `cost` decimal(10, 2) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `order` (`order_id`, `user_id`, `user_currency`, `email`, `street_address`, `city`, `state`, `country`, `zip_code`, `order_state`) VALUES (1, 1, 'USD', 'testuser@example.com', '123 Test St', 'Test City', 'Test State', 'Test Country', 12345, 'placed');
INSERT INTO `order_item` (`product_id`, `order_id`, `quantity`, `cost`) VALUES (1, 1, 2, 39.98);

USE `payment`;

CREATE TABLE IF NOT EXISTS `payment` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL,
    `order_id` bigint(20) NOT NULL,
    `transaction_id` bigint(20) NOT NULL,
    `amount` decimal(10, 2) NOT NULL,
    `pay_at` datetime NOT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `payment` (`user_id`, `order_id`, `transaction_id`, `amount`, `pay_at`) VALUES (1, 1, 100000001, 39.98, NOW());