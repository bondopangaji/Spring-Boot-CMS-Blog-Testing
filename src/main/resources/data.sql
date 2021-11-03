SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


INSERT IGNORE INTO `author` (`id`, `name`, `email`, `password`, `created_at`) VALUES
(1, 'Author', 'author@bondopangaji.com', '5f4dcc3b5aa765d61d8327deb882cf99', '2021-10-29 17:27:46');

INSERT IGNORE INTO `category` (`id`, `name`, `description`, `created_at`) VALUES
(1, 'Blog', 'lorem ipsum', '2021-10-30 15:06:57'),
(2, 'Hobby', 'lorem ipsum', '2021-10-30 15:07:07'),
(3, 'Sport', 'lorem ipsum', '2021-10-30 15:07:17'),
(4, 'Programming', 'lorem ipsum', '2021-10-30 15:13:04');

INSERT IGNORE INTO `post` (`id`, `title`, `content`, `author_id`, `category_id`, `created_at`) VALUES
(15, 'How to Javascript', 'Post about javascript', 1, 4, '2021-10-30 15:39:15');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
