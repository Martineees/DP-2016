-- phpMyAdmin SQL Dump
-- version 4.5.0.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 01, 2017 at 02:56 PM
-- Server version: 10.0.17-MariaDB
-- PHP Version: 5.6.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ar_quiz_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `answers`
--

CREATE TABLE `answers` (
  `id` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `question_id` int(11) NOT NULL,
  `is_correct` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `answers`
--

INSERT INTO `answers` (`id`, `name`, `question_id`, `is_correct`) VALUES
(1, 'Správna odpoveď', 1, 1),
(2, 'Nesprávna odpoveď 1', 1, 0),
(3, 'Nesprávna odpoveď 2', 1, 0),
(4, 'Nesprávna odpoveď 3', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `competitions`
--

CREATE TABLE `competitions` (
  `id` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `owner` int(11) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `competitions`
--

INSERT INTO `competitions` (`id`, `name`, `owner`, `created`) VALUES
(1, 'cmpSkuska1', 1, '2017-02-28 15:21:54');

-- --------------------------------------------------------

--
-- Table structure for table `competitors`
--

CREATE TABLE `competitors` (
  `id` int(11) NOT NULL,
  `competition_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE `location` (
  `id` int(11) NOT NULL,
  `block` varchar(1) NOT NULL,
  `floor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`id`, `block`, `floor`) VALUES
(1, 'D', 7),
(2, 'D', 6),
(3, 'D', 5),
(4, 'D', 4),
(5, 'D', 3),
(6, 'D', 2),
(7, 'D', 1),
(8, 'D', 0),
(9, 'A', 8),
(10, 'A', 7),
(11, 'A', 6),
(12, 'A', 5),
(13, 'A', 4),
(14, 'A', 3),
(15, 'A', 2),
(16, 'A', 1),
(17, 'A', 0),
(18, 'B', 7),
(19, 'B', 6),
(20, 'B', 5),
(21, 'B', 4),
(22, 'B', 3),
(23, 'B', 2),
(24, 'B', 1),
(25, 'B', 0),
(26, 'C', 8),
(27, 'C', 7),
(28, 'C', 6),
(29, 'C', 5),
(30, 'C', 4),
(31, 'C', 3),
(32, 'C', 2),
(33, 'C', 1),
(34, 'C', 0),
(35, 'Y', 0);

-- --------------------------------------------------------

--
-- Table structure for table `measurement`
--

CREATE TABLE `measurement` (
  `id` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `wifi_id` int(11) NOT NULL,
  `block_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `measurement`
--

INSERT INTO `measurement` (`id`, `level`, `wifi_id`, `block_id`) VALUES
(1, -67, 1, 1),
(2, -86, 2, 1),
(3, -73, 3, 1),
(4, -97, 4, 1),
(5, -87, 5, 1),
(6, -97, 4, 1),
(7, -71, 1, 1),
(8, -94, 5, 1),
(9, -91, 2, 1),
(10, -85, 3, 1),
(11, -95, 6, 1),
(12, -97, 7, 1),
(13, -67, 1, 1),
(14, -97, 4, 1),
(15, -95, 5, 1),
(16, -93, 2, 1),
(17, -81, 3, 1),
(18, -97, 7, 1),
(19, -64, 1, 1),
(20, -97, 6, 1),
(21, -81, 3, 1),
(22, -97, 5, 1),
(23, -89, 2, 1),
(24, -97, 4, 1),
(25, -87, 8, 2),
(26, -86, 6, 2),
(27, -64, 1, 2),
(28, -97, 3, 2),
(29, -88, 7, 2),
(30, -87, 4, 2),
(31, -75, 5, 2),
(32, -68, 2, 2),
(33, -94, 9, 2),
(34, -97, 3, 2),
(35, -80, 2, 2),
(36, -82, 8, 2),
(37, -81, 5, 2),
(38, -78, 1, 2),
(39, -86, 7, 2),
(40, -94, 9, 2),
(41, -81, 6, 2),
(42, -83, 4, 2),
(43, -95, 9, 2),
(44, -89, 1, 2),
(45, -75, 5, 2),
(46, -87, 7, 2),
(47, -86, 8, 2),
(48, -70, 2, 2),
(49, -89, 4, 2),
(50, -89, 6, 2),
(51, -75, 5, 2),
(52, -89, 3, 2),
(53, -86, 8, 2),
(54, -85, 1, 2),
(55, -99, 10, 2),
(56, -89, 7, 2),
(57, -87, 4, 2),
(58, -93, 9, 2),
(59, -74, 2, 2),
(60, -87, 6, 2),
(61, -83, 11, 3),
(62, -74, 12, 3),
(63, -83, 13, 3),
(64, -73, 14, 3),
(65, -94, 7, 3),
(66, -91, 1, 3),
(67, -94, 5, 3),
(68, -84, 2, 3),
(69, -73, 15, 3),
(70, -94, 7, 3),
(71, -88, 15, 3),
(72, -91, 1, 3),
(73, -85, 12, 3),
(74, -93, 16, 3),
(75, -93, 2, 3),
(76, -95, 6, 3),
(77, -87, 14, 3),
(78, -94, 5, 3),
(79, -93, 11, 3),
(80, -88, 13, 3),
(81, -93, 17, 3),
(82, -90, 15, 3),
(83, -97, 18, 3),
(84, -95, 17, 3),
(85, -93, 11, 3),
(86, -95, 6, 3),
(87, -87, 19, 3),
(88, -95, 13, 3),
(89, -86, 16, 3),
(90, -94, 8, 3),
(91, -86, 14, 3),
(92, -97, 5, 3),
(93, -90, 12, 3),
(94, -86, 2, 3),
(95, -87, 18, 3),
(96, -88, 6, 3),
(97, -84, 2, 3),
(98, -78, 19, 3),
(99, -89, 11, 3),
(100, -99, 16, 3),
(101, -95, 17, 3),
(102, -86, 5, 3),
(103, -84, 15, 3),
(104, -89, 8, 3),
(105, -84, 12, 3),
(106, -94, 13, 3),
(107, -83, 14, 3),
(108, -92, 14, 4),
(109, -91, 20, 4),
(110, -91, 15, 4),
(111, -93, 12, 4),
(112, -91, 17, 4),
(113, -82, 18, 4),
(114, -96, 21, 4),
(115, -91, 22, 4),
(116, -65, 11, 4),
(117, -88, 16, 4),
(118, -76, 13, 4),
(119, -96, 15, 4),
(120, -91, 22, 4),
(121, -91, 20, 4),
(122, -73, 11, 4),
(123, -95, 14, 4),
(124, -85, 17, 4),
(125, -94, 12, 4),
(126, -82, 18, 4),
(127, -86, 23, 4),
(128, -96, 21, 4),
(129, -83, 16, 4),
(130, -78, 13, 4),
(131, -84, 16, 4),
(132, -96, 21, 4),
(133, -87, 23, 4),
(134, -98, 15, 4),
(135, -95, 20, 4),
(136, -91, 22, 4),
(137, -94, 12, 4),
(138, -95, 24, 4),
(139, -82, 13, 4),
(140, -83, 17, 4),
(141, -79, 18, 4),
(142, -95, 14, 4),
(143, -88, 11, 4),
(144, -93, 25, 4),
(145, -95, 14, 4),
(146, -81, 11, 4),
(147, -85, 13, 4),
(148, -94, 12, 4),
(149, -79, 18, 4),
(150, -79, 16, 4),
(151, -95, 20, 4),
(152, -95, 24, 4),
(153, -98, 15, 4),
(154, -81, 23, 4),
(155, -85, 17, 4),
(156, -98, 15, 5),
(157, -75, 26, 5),
(158, -81, 17, 5),
(159, -52, 11, 5),
(160, -90, 27, 5),
(161, -71, 22, 5),
(162, -80, 16, 5),
(163, -98, 28, 5),
(164, -86, 24, 5),
(165, -81, 23, 5),
(166, -95, 14, 5),
(167, -83, 29, 5),
(168, -79, 18, 5),
(169, -94, 30, 5),
(170, -94, 12, 5),
(171, -90, 31, 5),
(172, -51, 13, 5),
(173, -80, 21, 5),
(174, -93, 25, 5),
(175, -74, 20, 5),
(176, -90, 25, 5),
(177, -94, 31, 5),
(178, -74, 20, 5),
(179, -90, 22, 5),
(180, -94, 30, 5),
(181, -62, 13, 5),
(182, -80, 21, 5),
(183, -86, 24, 5),
(184, -90, 27, 5),
(185, -72, 17, 5),
(186, -98, 28, 5),
(187, -93, 29, 5),
(188, -94, 26, 5),
(189, -79, 18, 5),
(190, -60, 11, 5),
(191, -97, 16, 5),
(192, -74, 20, 5),
(193, -96, 21, 5),
(194, -69, 11, 5),
(195, -69, 13, 5),
(196, -83, 29, 5),
(197, -90, 27, 5),
(198, -75, 24, 5),
(199, -86, 22, 5),
(200, -79, 18, 5),
(201, -97, 16, 5),
(202, -98, 28, 5),
(203, -94, 30, 5),
(204, -93, 31, 5),
(205, -81, 25, 5),
(206, -69, 17, 5),
(207, -94, 26, 5),
(208, -86, 24, 5),
(209, -94, 16, 5),
(210, -86, 22, 5),
(211, -82, 26, 5),
(212, -67, 13, 5),
(213, -80, 25, 5),
(214, -93, 31, 5),
(215, -80, 17, 5),
(216, -88, 21, 5),
(217, -83, 29, 5),
(218, -65, 11, 5),
(219, -95, 24, 6),
(220, -86, 13, 6),
(221, -86, 32, 6),
(222, -76, 28, 6),
(223, -81, 30, 6),
(224, -69, 11, 6),
(225, -74, 27, 6),
(226, -96, 26, 6),
(227, -88, 21, 6),
(228, -76, 33, 6),
(229, -83, 29, 6),
(230, -84, 22, 6),
(231, -94, 16, 6),
(232, -67, 31, 6),
(233, -80, 25, 6),
(234, -76, 34, 6),
(235, -98, 35, 6),
(236, -65, 17, 6),
(237, -88, 36, 6),
(238, -72, 31, 6),
(239, -86, 32, 6),
(240, -81, 27, 6),
(241, -95, 24, 6),
(242, -93, 22, 6),
(243, -80, 25, 6),
(244, -76, 34, 6),
(245, -88, 21, 6),
(246, -89, 30, 6),
(247, -96, 35, 6),
(248, -77, 17, 6),
(249, -69, 11, 6),
(250, -75, 33, 6),
(251, -82, 13, 6),
(252, -88, 36, 6),
(253, -94, 16, 6),
(254, -96, 26, 6),
(255, -75, 28, 6),
(256, -84, 30, 6),
(257, -69, 11, 6),
(258, -88, 36, 6),
(259, -94, 16, 6),
(260, -75, 28, 6),
(261, -86, 32, 6),
(262, -74, 34, 6),
(263, -73, 27, 6),
(264, -74, 33, 6),
(265, -88, 21, 6),
(266, -80, 25, 6),
(267, -96, 26, 6),
(268, -96, 35, 6),
(269, -78, 13, 6),
(270, -94, 24, 6),
(271, -61, 31, 6),
(272, -93, 22, 6),
(273, -52, 17, 6),
(274, -86, 34, 6),
(275, -87, 36, 6),
(276, -83, 28, 6),
(277, -86, 33, 6),
(278, -89, 29, 6),
(279, -93, 22, 6),
(280, -85, 27, 6),
(281, -94, 24, 6),
(282, -78, 13, 6),
(283, -95, 11, 6),
(284, -90, 25, 6),
(285, -80, 31, 6),
(286, -61, 17, 6),
(287, -96, 35, 6),
(288, -86, 30, 6),
(289, -78, 13, 7),
(290, -89, 29, 7),
(291, -90, 25, 7),
(292, -86, 30, 7),
(293, -94, 24, 7),
(294, -94, 17, 7),
(295, -93, 22, 7),
(296, -73, 35, 7),
(297, -84, 37, 7),
(298, -81, 38, 7),
(299, -87, 36, 7),
(300, -91, 27, 7),
(301, -86, 33, 7),
(302, -84, 31, 7),
(303, -92, 39, 7),
(304, -83, 28, 7),
(305, -85, 40, 7),
(306, -86, 34, 7),
(307, -86, 41, 7),
(308, -85, 42, 7),
(309, -80, 43, 7),
(310, -84, 11, 7),
(311, -80, 42, 7),
(312, -90, 25, 7),
(313, -87, 36, 7),
(314, -83, 28, 7),
(315, -90, 38, 7),
(316, -80, 43, 7),
(317, -89, 29, 7),
(318, -78, 13, 7),
(319, -85, 37, 7),
(320, -80, 41, 7),
(321, -82, 35, 7),
(322, -84, 31, 7),
(323, -81, 17, 7),
(324, -92, 39, 7),
(325, -77, 44, 7),
(326, -84, 40, 7),
(327, -86, 33, 7),
(328, -86, 34, 7),
(329, -90, 27, 7),
(330, -94, 24, 7),
(331, -98, 11, 7),
(332, -77, 45, 7),
(333, -91, 46, 7),
(334, -86, 30, 7),
(335, -89, 29, 7),
(336, -95, 38, 7),
(337, -98, 43, 7),
(338, -85, 37, 7),
(339, -91, 45, 7),
(340, -80, 41, 7),
(341, -90, 25, 7),
(342, -80, 42, 7),
(343, -92, 39, 7),
(344, -97, 31, 7),
(345, -98, 11, 7),
(346, -71, 17, 7),
(347, -84, 40, 7),
(348, -78, 13, 7),
(349, -97, 30, 7),
(350, -83, 28, 7),
(351, -89, 47, 7),
(352, -91, 46, 7),
(353, -86, 33, 7),
(354, -93, 35, 7),
(355, -97, 27, 7),
(356, -91, 44, 7),
(357, -87, 36, 7),
(358, -86, 34, 7),
(359, -69, 40, 7),
(360, -87, 46, 7),
(361, -80, 35, 7),
(362, -81, 37, 7),
(363, -85, 43, 7),
(364, -96, 30, 7),
(365, -88, 41, 7),
(366, -87, 17, 7),
(367, -99, 11, 7),
(368, -93, 44, 7),
(369, -96, 31, 7),
(370, -88, 38, 7),
(371, -86, 47, 7),
(372, -89, 45, 7),
(373, -88, 42, 7),
(374, -99, 27, 7),
(375, -93, 48, 8),
(376, -64, 47, 8),
(377, -94, 49, 8),
(378, -88, 50, 8),
(379, -87, 51, 8),
(380, -91, 52, 8),
(381, -93, 53, 8),
(382, -87, 54, 8),
(383, -67, 46, 8),
(384, -87, 55, 8),
(385, -98, 35, 8),
(386, -88, 56, 8),
(387, -86, 57, 8),
(388, -96, 58, 8),
(389, -90, 55, 8),
(390, -95, 53, 8),
(391, -90, 51, 8),
(392, -96, 49, 8),
(393, -65, 46, 8),
(394, -98, 59, 8),
(395, -67, 47, 8),
(396, -89, 57, 8),
(397, -86, 50, 8),
(398, -96, 58, 8),
(399, -88, 40, 8),
(400, -85, 56, 8),
(401, -87, 48, 8),
(402, -97, 53, 8),
(403, -90, 44, 8),
(404, -94, 59, 8),
(405, -95, 49, 8),
(406, -74, 50, 8),
(407, -61, 46, 8),
(408, -96, 60, 8),
(409, -90, 52, 8),
(410, -89, 40, 8),
(411, -88, 55, 8),
(412, -93, 57, 8),
(413, -92, 54, 8),
(414, -93, 41, 8),
(415, -59, 47, 8),
(416, -91, 45, 8),
(417, -95, 17, 8),
(418, -89, 51, 8),
(419, -95, 58, 8),
(420, -91, 40, 8),
(421, -92, 55, 8),
(422, -93, 57, 8),
(423, -91, 51, 8),
(424, -85, 48, 8),
(425, -97, 49, 8),
(426, -90, 52, 8),
(427, -57, 46, 8),
(428, -86, 50, 8),
(429, -88, 54, 8),
(430, -95, 17, 8),
(431, -90, 45, 8),
(432, -53, 47, 8),
(433, -85, 56, 8),
(434, -94, 59, 8),
(435, -91, 44, 8),
(436, -92, 61, 9),
(437, -89, 62, 9),
(438, -94, 62, 9),
(439, -95, 62, 9),
(440, -92, 61, 9),
(441, -92, 62, 9),
(442, -86, 61, 9),
(443, -79, 61, 10),
(444, -72, 62, 10),
(445, -74, 62, 10),
(446, -80, 61, 10),
(447, -97, 63, 10),
(448, -95, 64, 10),
(449, -85, 62, 10),
(450, -75, 61, 10),
(451, -96, 65, 10),
(452, -74, 61, 10),
(453, -66, 62, 10),
(454, -82, 66, 11),
(455, -96, 67, 11),
(456, -84, 62, 11),
(457, -94, 62, 11),
(458, -86, 66, 11),
(459, -99, 67, 11),
(460, -93, 61, 11),
(461, -90, 62, 11),
(462, -88, 66, 11),
(463, -100, 67, 11),
(464, -96, 62, 11),
(465, -88, 66, 11),
(466, -90, 62, 12),
(467, -96, 68, 12),
(468, -92, 69, 12),
(469, -89, 69, 12),
(470, -99, 68, 12),
(471, -100, 70, 12),
(472, -97, 68, 12),
(473, -89, 69, 12),
(474, -84, 69, 12),
(475, -89, 70, 12),
(476, -88, 68, 12),
(477, -93, 68, 13),
(478, -92, 71, 13),
(479, -90, 69, 13),
(480, -96, 70, 13),
(481, -97, 72, 13),
(482, -98, 73, 13),
(483, -92, 73, 13),
(484, -94, 73, 13),
(485, -92, 74, 14),
(486, -99, 75, 14),
(487, -86, 73, 14),
(488, -100, 74, 14),
(489, -98, 75, 14),
(490, -75, 73, 14),
(491, -99, 75, 14),
(492, -100, 74, 14),
(493, -82, 73, 14),
(494, -91, 74, 14),
(495, -95, 76, 14),
(496, -89, 73, 14),
(497, -89, 73, 15),
(498, -85, 73, 15),
(499, -78, 73, 15),
(500, -96, 72, 15),
(501, -87, 73, 15),
(502, -91, 77, 16),
(503, -87, 73, 16),
(504, -80, 78, 16),
(505, -77, 79, 16),
(506, -91, 80, 16),
(507, -80, 78, 16),
(508, -93, 73, 16),
(509, -77, 79, 16),
(510, -94, 77, 16),
(511, -91, 81, 16),
(512, -92, 77, 16),
(513, -85, 79, 16),
(514, -85, 77, 16),
(515, -85, 78, 16),
(516, -68, 81, 17),
(517, -68, 80, 17),
(518, -93, 77, 17),
(519, -95, 82, 17),
(520, -88, 79, 17),
(521, -88, 78, 17),
(522, -68, 80, 17),
(523, -96, 82, 17),
(524, -88, 79, 17),
(525, -93, 77, 17),
(526, -73, 81, 17),
(527, -88, 78, 17),
(528, -96, 82, 17),
(529, -72, 81, 17),
(530, -68, 80, 17),
(531, -58, 81, 17),
(532, -57, 80, 17),
(533, -94, 82, 17),
(534, -94, 83, 18),
(535, -96, 84, 18),
(536, -83, 85, 18),
(537, -87, 86, 18),
(538, -83, 85, 18),
(539, -91, 86, 18),
(540, -95, 84, 18),
(541, -93, 87, 18),
(542, -88, 86, 18),
(543, -97, 85, 18),
(544, -100, 84, 18),
(545, -97, 83, 18),
(546, -88, 85, 18),
(547, -98, 88, 18),
(548, -94, 84, 18),
(549, -95, 86, 18),
(550, -99, 89, 19),
(551, -92, 83, 19),
(552, -67, 85, 19),
(553, -95, 88, 19),
(554, -90, 84, 19),
(555, -81, 86, 19),
(556, -97, 87, 19),
(557, -80, 86, 19),
(558, -97, 88, 19),
(559, -91, 84, 19),
(560, -95, 83, 19),
(561, -90, 85, 19),
(562, -86, 83, 19),
(563, -87, 84, 19),
(564, -69, 86, 19),
(565, -81, 85, 19),
(566, -93, 90, 19),
(567, -91, 88, 19),
(568, -86, 84, 19),
(569, -87, 88, 19),
(570, -88, 83, 19),
(571, -76, 85, 19),
(572, -76, 86, 19),
(573, -97, 90, 19),
(574, -96, 91, 20),
(575, -93, 90, 20),
(576, -65, 84, 20),
(577, -95, 89, 20),
(578, -85, 87, 20),
(579, -95, 92, 20),
(580, -95, 93, 20),
(581, -88, 94, 20),
(582, -94, 95, 20),
(583, -85, 88, 20),
(584, -76, 83, 20),
(585, -97, 86, 20),
(586, -86, 85, 20),
(587, -93, 85, 20),
(588, -96, 91, 20),
(589, -88, 90, 20),
(590, -74, 84, 20),
(591, -93, 86, 20),
(592, -91, 89, 20),
(593, -94, 95, 20),
(594, -94, 93, 20),
(595, -92, 87, 20),
(596, -88, 88, 20),
(597, -73, 83, 20),
(598, -76, 86, 20),
(599, -55, 84, 20),
(600, -76, 88, 20),
(601, -94, 89, 20),
(602, -93, 87, 20),
(603, -95, 85, 20),
(604, -97, 95, 20),
(605, -93, 92, 20),
(606, -76, 90, 20),
(607, -67, 83, 20),
(608, -96, 89, 20),
(609, -92, 92, 20),
(610, -89, 86, 20),
(611, -85, 88, 20),
(612, -91, 90, 20),
(613, -93, 93, 20),
(614, -93, 96, 20),
(615, -87, 94, 20),
(616, -80, 87, 20),
(617, -78, 83, 20),
(618, -70, 84, 20),
(619, -92, 85, 20),
(620, -76, 95, 21),
(621, -76, 90, 21),
(622, -92, 89, 21),
(623, -92, 85, 21),
(624, -93, 83, 21),
(625, -99, 87, 21),
(626, -93, 93, 21),
(627, -63, 88, 21),
(628, -74, 97, 21),
(629, -88, 96, 21),
(630, -94, 98, 21),
(631, -92, 92, 21),
(632, -81, 86, 21),
(633, -91, 84, 21),
(634, -96, 86, 21),
(635, -96, 89, 21),
(636, -93, 98, 21),
(637, -67, 88, 21),
(638, -90, 84, 21),
(639, -94, 83, 21),
(640, -85, 95, 21),
(641, -78, 90, 21),
(642, -81, 97, 21),
(643, -95, 96, 21),
(644, -94, 98, 21),
(645, -60, 90, 21),
(646, -82, 97, 21),
(647, -83, 95, 21),
(648, -95, 96, 21),
(649, -96, 99, 21),
(650, -88, 84, 21),
(651, -62, 88, 21),
(652, -91, 83, 21),
(653, -80, 95, 21),
(654, -62, 88, 21),
(655, -96, 98, 21),
(656, -70, 90, 21),
(657, -96, 86, 21),
(658, -89, 83, 21),
(659, -87, 97, 21),
(660, -89, 96, 21),
(661, -96, 87, 21),
(662, -91, 84, 21),
(663, -95, 90, 22),
(664, -89, 100, 22),
(665, -95, 97, 22),
(666, -93, 99, 22),
(667, -97, 95, 22),
(668, -87, 88, 22),
(669, -97, 99, 22),
(670, -82, 88, 22),
(671, -89, 100, 22),
(672, -92, 97, 22),
(673, -91, 95, 22),
(674, -89, 90, 22),
(675, -93, 84, 22),
(676, -89, 100, 22),
(677, -91, 99, 22),
(678, -82, 90, 22),
(679, -79, 88, 22),
(680, -93, 83, 22),
(681, -95, 95, 22),
(682, -89, 100, 22),
(683, -87, 90, 22),
(684, -92, 83, 22),
(685, -97, 84, 22),
(686, -82, 88, 22),
(687, -89, 99, 22),
(688, -97, 95, 22),
(689, -95, 90, 23),
(690, -94, 88, 23),
(691, -97, 101, 23),
(692, -83, 71, 23),
(693, -97, 99, 23),
(694, -97, 102, 23),
(695, -97, 103, 23),
(696, -89, 71, 23),
(697, -91, 103, 23),
(698, -98, 99, 23),
(699, -88, 103, 23),
(700, -96, 104, 23),
(701, -95, 88, 23),
(702, -90, 71, 23),
(703, -94, 90, 23),
(704, -94, 104, 23),
(705, -98, 99, 23),
(706, -93, 88, 23),
(707, -89, 103, 23),
(708, -93, 71, 23),
(709, -88, 105, 24),
(710, -89, 106, 24),
(711, -86, 104, 24),
(712, -88, 107, 24),
(713, -73, 103, 24),
(714, -95, 71, 24),
(715, -94, 77, 24),
(716, -88, 108, 24),
(717, -89, 109, 24),
(718, -92, 110, 24),
(719, -81, 111, 24),
(720, -87, 104, 24),
(721, -85, 110, 24),
(722, -89, 77, 24),
(723, -79, 111, 24),
(724, -86, 103, 24),
(725, -95, 71, 24),
(726, -88, 107, 24),
(727, -84, 108, 24),
(728, -88, 105, 24),
(729, -88, 109, 24),
(730, -88, 106, 24),
(731, -96, 111, 24),
(732, -89, 110, 24),
(733, -88, 106, 24),
(734, -81, 104, 24),
(735, -74, 103, 24),
(736, -89, 108, 24),
(737, -99, 77, 24),
(738, -95, 71, 24),
(739, -87, 107, 24),
(740, -91, 105, 24),
(741, -99, 77, 24),
(742, -89, 112, 24),
(743, -79, 104, 24),
(744, -89, 109, 24),
(745, -65, 103, 24),
(746, -80, 110, 24),
(747, -89, 113, 24),
(748, -87, 112, 25),
(749, -93, 114, 25),
(750, -90, 105, 25),
(751, -89, 108, 25),
(752, -89, 115, 25),
(753, -87, 113, 25),
(754, -96, 77, 25),
(755, -91, 111, 25),
(756, -54, 107, 25),
(757, -75, 104, 25),
(758, -98, 103, 25),
(759, -65, 107, 25),
(760, -65, 105, 25),
(761, -87, 115, 25),
(762, -98, 103, 25),
(763, -88, 116, 25),
(764, -96, 77, 25),
(765, -91, 111, 25),
(766, -75, 104, 25),
(767, -89, 108, 25),
(768, -87, 113, 25),
(769, -93, 114, 25),
(770, -87, 112, 25),
(771, -83, 115, 25),
(772, -65, 105, 25),
(773, -65, 107, 25),
(774, -88, 77, 25),
(775, -87, 104, 25),
(776, -84, 116, 25),
(777, -85, 103, 25),
(778, -93, 114, 25),
(779, -83, 115, 25),
(780, -89, 103, 25),
(781, -81, 77, 25),
(782, -82, 116, 25),
(783, -88, 104, 25),
(784, -92, 117, 25),
(785, -75, 118, 25),
(786, -75, 114, 25),
(787, -67, 107, 25),
(788, -67, 105, 25),
(789, -96, 119, 26),
(790, -75, 120, 26),
(791, -94, 121, 26),
(792, -94, 122, 26),
(793, -95, 123, 26),
(794, -96, 122, 26),
(795, -69, 120, 26),
(796, -94, 121, 26),
(797, -99, 121, 26),
(798, -70, 120, 26),
(799, -97, 119, 26),
(800, -99, 121, 26),
(801, -99, 119, 26),
(802, -97, 122, 26),
(803, -65, 120, 26),
(804, -99, 119, 27),
(805, -88, 123, 27),
(806, -84, 122, 27),
(807, -99, 124, 27),
(808, -86, 121, 27),
(809, -55, 120, 27),
(810, -97, 119, 27),
(811, -63, 120, 27),
(812, -91, 122, 27),
(813, -99, 124, 27),
(814, -88, 121, 27),
(815, -91, 123, 27),
(816, -95, 119, 27),
(817, -80, 121, 27),
(818, -95, 123, 27),
(819, -96, 124, 27),
(820, -88, 122, 27),
(821, -65, 120, 27),
(822, -97, 124, 27),
(823, -80, 122, 27),
(824, -94, 123, 27),
(825, -84, 121, 27),
(826, -93, 119, 27),
(827, -65, 120, 27),
(828, -71, 120, 28),
(829, -94, 125, 28),
(830, -74, 123, 28),
(831, -95, 126, 28),
(832, -97, 127, 28),
(833, -66, 121, 28),
(834, -95, 126, 28),
(835, -97, 125, 28),
(836, -95, 122, 28),
(837, -72, 120, 28),
(838, -73, 123, 28),
(839, -71, 121, 28),
(840, -93, 126, 28),
(841, -80, 123, 28),
(842, -65, 121, 28),
(843, -93, 125, 28),
(844, -85, 120, 28),
(845, -87, 120, 28),
(846, -95, 126, 28),
(847, -82, 123, 28),
(848, -97, 125, 28),
(849, -63, 121, 28),
(850, -88, 120, 29),
(851, -83, 126, 29),
(852, -95, 128, 29),
(853, -87, 129, 29),
(854, -90, 127, 29),
(855, -97, 130, 29),
(856, -87, 121, 29),
(857, -84, 125, 29),
(858, -94, 123, 29),
(859, -90, 120, 29),
(860, -89, 126, 29),
(861, -94, 123, 29),
(862, -79, 121, 29),
(863, -93, 127, 29),
(864, -89, 125, 29),
(865, -85, 126, 29),
(866, -86, 121, 29),
(867, -91, 129, 29),
(868, -85, 125, 29),
(869, -92, 127, 29),
(870, -95, 128, 29),
(871, -94, 123, 29),
(872, -89, 120, 29),
(873, -89, 126, 29),
(874, -91, 123, 29),
(875, -93, 129, 29),
(876, -89, 125, 29),
(877, -92, 127, 29),
(878, -85, 120, 29),
(879, -90, 128, 29),
(880, -92, 121, 29),
(881, -99, 126, 30),
(882, -96, 123, 30),
(883, -95, 131, 30),
(884, -88, 130, 30),
(885, -96, 125, 30),
(886, -77, 127, 30),
(887, -91, 132, 30),
(888, -98, 121, 30),
(889, -78, 127, 30),
(890, -91, 130, 30),
(891, -97, 121, 30),
(892, -97, 125, 30),
(893, -97, 120, 30),
(894, -95, 126, 30),
(895, -95, 125, 30),
(896, -78, 127, 30),
(897, -95, 133, 30),
(898, -97, 121, 30),
(899, -88, 130, 30),
(900, -97, 120, 30),
(901, -97, 133, 30),
(902, -99, 121, 30),
(903, -96, 132, 30),
(904, -97, 125, 30),
(905, -97, 134, 30),
(906, -95, 131, 30),
(907, -79, 130, 30),
(908, -83, 127, 30),
(909, -97, 126, 30),
(910, -86, 134, 31),
(911, -93, 133, 31),
(912, -54, 127, 31),
(913, -97, 135, 31),
(914, -96, 135, 31),
(915, -95, 133, 31),
(916, -88, 134, 31),
(917, -52, 127, 31),
(918, -60, 127, 31),
(919, -94, 134, 31),
(920, -90, 133, 31),
(921, -96, 135, 31),
(922, -89, 133, 31),
(923, -62, 127, 31),
(924, -99, 130, 31),
(925, -95, 134, 31),
(926, -84, 133, 32),
(927, -81, 127, 32),
(928, -88, 135, 32),
(929, -74, 133, 32),
(930, -71, 127, 32),
(931, -93, 135, 32),
(932, -74, 127, 32),
(933, -94, 135, 32),
(934, -78, 133, 32),
(935, -94, 135, 32),
(936, -77, 133, 32),
(937, -88, 127, 32),
(938, -96, 135, 33),
(939, -93, 133, 33),
(940, -86, 127, 33),
(941, -86, 136, 33),
(942, -83, 137, 33),
(943, -86, 138, 33),
(944, -91, 139, 33),
(945, -91, 136, 33),
(946, -85, 127, 33),
(947, -91, 138, 33),
(948, -92, 140, 33),
(949, -83, 137, 33),
(950, -92, 141, 33),
(951, -93, 133, 33),
(952, -90, 137, 33),
(953, -86, 133, 33),
(954, -88, 140, 33),
(955, -86, 141, 33),
(956, -85, 139, 33),
(957, -83, 138, 33),
(958, -87, 127, 33),
(959, -83, 136, 33),
(960, -83, 138, 33),
(961, -86, 141, 33),
(962, -85, 139, 33),
(963, -84, 136, 33),
(964, -90, 133, 33),
(965, -95, 127, 33),
(966, -90, 137, 33),
(967, -88, 140, 33),
(968, -86, 141, 34),
(969, -83, 138, 34),
(970, -85, 139, 34),
(971, -97, 142, 34),
(972, -91, 127, 34),
(973, -65, 140, 34),
(974, -69, 137, 34),
(975, -84, 136, 34),
(976, -90, 133, 34),
(977, -65, 140, 34),
(978, -69, 137, 34),
(979, -86, 141, 34),
(980, -85, 139, 34),
(981, -84, 136, 34),
(982, -97, 142, 34),
(983, -91, 127, 34),
(984, -83, 138, 34),
(985, -90, 133, 34),
(986, -87, 137, 34),
(987, -87, 140, 34),
(988, -91, 143, 34),
(989, -89, 138, 34),
(990, -91, 144, 34),
(991, -97, 142, 34),
(992, -91, 136, 34),
(993, -77, 144, 34),
(994, -89, 138, 34),
(995, -87, 137, 34),
(996, -87, 140, 34),
(997, -91, 136, 34),
(998, -87, 142, 34),
(999, -77, 143, 34),
(1000, -42, 145, 35);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `competition_id` int(11) NOT NULL,
  `target_id` varchar(256) NOT NULL,
  `location_id` int(11) NOT NULL,
  `type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `name`, `competition_id`, `target_id`, `location_id`, `type`) VALUES
(1, 'Otázka č1 ?', 1, 'e8c302453364462cbc2d93a3f46c3255', 16, 0);

-- --------------------------------------------------------

--
-- Table structure for table `score`
--

CREATE TABLE `score` (
  `id` int(11) NOT NULL,
  `competitor_id` int(11) NOT NULL,
  `value` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `password_hash` varchar(256) NOT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `password_hash`, `is_admin`) VALUES
(1, 'skuska1', '$2y$10$H3K70PakNoLXo8mFGMaulejC88i8CQRQHGfN1A6iRCezgXNjlSgl2', 0);

-- --------------------------------------------------------

--
-- Table structure for table `wifi`
--

CREATE TABLE `wifi` (
  `id` int(11) NOT NULL,
  `ssid` varchar(100) NOT NULL,
  `mac` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `wifi`
--

INSERT INTO `wifi` (`id`, `ssid`, `mac`) VALUES
(1, 'eduroam', '00:08:30:b6:26:f0'),
(2, 'MARTIN-PC_Wifi', 'c8:3a:35:42:be:58'),
(3, 'eduroam', '64:ae:0c:25:8f:40'),
(4, '', '54:78:1a:21:06:60'),
(5, 'Komnata_D610', '00:24:01:8d:2b:da'),
(6, 'eduroam', '54:78:1a:21:06:62'),
(7, 'D606-Network', '00:11:09:0d:06:ad'),
(8, '', '54:78:1a:21:06:61'),
(9, 'AE_WIFI', 'e0:3f:49:40:ad:38'),
(10, 'peterbeno_fei', '00:23:69:0c:6c:38'),
(11, '', '88:75:56:db:e2:a0'),
(12, '', '64:e9:50:af:95:b0'),
(13, 'eduroam', '88:75:56:db:e2:a1'),
(14, '', '64:e9:50:af:95:b1'),
(15, 'eduroam', '64:e9:50:af:95:b2'),
(16, 'eduroam', '58:97:1e:23:3c:91'),
(17, 'Accenture-Free', '00:23:5e:4b:b8:d0'),
(18, '', '58:97:1e:23:3c:90'),
(19, 'AE_WIFI_2EX', '80:1f:02:e8:98:9c'),
(20, '', '88:75:56:db:d9:50'),
(21, '', '88:75:56:db:b0:30'),
(22, 'eduroam', '88:75:56:db:d9:51'),
(23, 'DIRECT-TZWorkCentre 3225', '9e:93:4e:43:72:14'),
(24, 'OSK-597544', 'c4:6e:1f:59:75:44'),
(25, 'eduroam', '88:75:56:83:74:b1'),
(26, 'eduroam', '88:75:56:db:b0:31'),
(27, 'ADB-7FC5E5', 'a4:5d:a1:7f:c5:e5'),
(28, '', 'd4:ca:6d:d7:55:9f'),
(29, '', '88:75:56:83:74:b0'),
(30, 'Accenture-internal', '68:bc:0c:63:57:10'),
(31, 'EVOLVEO WINPC X5', '30:f7:d7:04:94:1a'),
(32, 'ZyXEL7245cc', 'ee:43:f6:72:45:cc'),
(33, 'ANV_01', 'd6:ca:6d:d7:55:9f'),
(34, 'ANV_GUEST', 'd6:ca:6d:d7:55:a0'),
(35, 'netD1', 'ac:22:0b:8d:8e:45'),
(36, 'AOWP', 'e4:71:85:32:4b:64'),
(37, 'FEI-FREE-1Mbit', '00:c8:8b:45:dc:81'),
(38, 'netD1', 'ac:22:0b:8d:94:d1'),
(39, 'NETGEAR', 'e0:91:f5:60:6b:22'),
(40, 'eduroam', '00:c8:8b:45:dc:82'),
(41, 'eduroam', '00:c8:8b:ab:a4:f2'),
(42, 'FEI-FREE-1Mbit', '00:c8:8b:ab:a4:f1'),
(43, 'netD1', 'ac:22:0b:8d:27:76'),
(44, 'FEI-FREE-1Mbit', '00:42:68:9c:8a:41'),
(45, 'eduroam', '00:42:68:9c:8a:42'),
(46, 'eduroam', '00:c8:8b:45:e0:d2'),
(47, 'FEI-FREE-1Mbit', '00:c8:8b:45:e0:d1'),
(48, 'eduroam', '00:42:68:6e:29:62'),
(49, 'ncr_stu', '0a:18:d6:e2:ae:9a'),
(50, 'FEI-FREE-1Mbit', '00:c8:8b:e8:7d:21'),
(51, 'FEI-FREE-1Mbit', '00:c8:8b:67:d3:51'),
(52, 'eduroam', '00:42:68:9c:19:22'),
(53, 'eduroam', '04:18:d6:e2:ae:9a'),
(54, 'FEI-FREE-1Mbit', '00:42:68:9c:19:21'),
(55, 'eduroam', '00:c8:8b:67:d3:52'),
(56, 'eduroam', '00:c8:8b:e8:7d:22'),
(57, 'FEI-FREE-1Mbit', '00:42:68:6e:29:61'),
(58, 'WR', '34:23:87:f7:4a:55'),
(59, 'CamareroTehlaAP', '2a:fc:f6:00:3c:32'),
(60, 'iTrojans', '2c:8a:72:b7:19:08'),
(61, 'KMECH253', '00:12:0e:51:84:c7'),
(62, 'KMECH', 'f0:79:59:c6:31:b4'),
(63, 'zvjs_guestnet', '00:e1:6d:4f:70:f0'),
(64, '', '00:e1:6d:4f:70:f1'),
(65, 'www.druzba.blava.net-scanning', '00:18:0a:01:07:a8'),
(66, 'KJFT', '00:40:f4:80:b5:d8'),
(67, '', '00:24:36:aa:c1:81'),
(68, 'H2020', 'c8:3a:35:0e:7b:d8'),
(69, 'TP-LINK_1C80D8', '00:27:19:1c:80:d8'),
(70, 'KJFT-WPA2', '80:1f:02:e5:9e:db'),
(71, 'steamlab', 'b0:48:7a:dc:ce:aa'),
(72, 'SimLab', '00:23:54:01:2b:35'),
(73, 'Allegro-512', '00:26:f2:24:96:f4'),
(74, 'GravityLab', 'f0:7d:68:49:6f:30'),
(75, 'AndroidAP', 'a8:81:95:6b:e2:9e'),
(76, 'A316', '60:a4:4c:eb:b7:8c'),
(77, 'SP_WiFi', '00:4f:62:01:e9:c0'),
(78, 'eduroam', '08:d0:9f:ed:e5:50'),
(79, 'FEI-FREE-1Mbit', '08:d0:9f:ed:e5:51'),
(80, 'eduroam', '00:c8:8b:9c:7b:02'),
(81, 'FEI-FREE-1Mbit', '00:c8:8b:9c:7b:01'),
(82, 'martin', 'f8:1a:67:3e:41:f0'),
(83, 'KTL', '00:1b:fc:df:0c:b7'),
(84, 'B-508', '34:08:04:14:60:07'),
(85, 'HQ', '4c:5e:0c:6e:10:49'),
(86, 'KTL', '00:1b:fc:df:0b:18'),
(87, 'B5posch-zapad', '00:18:f3:98:e4:af'),
(88, 'B-410', '4c:5e:0c:6a:f0:87'),
(89, 'B501', '00:1d:60:b6:56:2d'),
(90, 'KTL', '00:1e:8c:50:e5:d9'),
(91, 'LTE_Intranet', 'c0:4a:00:b6:9c:eb'),
(92, 'B-516', '00:1f:c6:ce:3f:d8'),
(93, 'hp_laserjet1020', '00:1c:f0:83:cd:e4'),
(94, 'B502', '00:13:d3:7f:23:a1'),
(95, 'MMCLab_public', '4c:5e:0c:71:5d:a2'),
(96, 'B-417', '4c:5e:0c:71:5e:08'),
(97, 'Multimedia1 B401', '4c:5e:0c:76:74:28'),
(98, 'B402', '34:08:04:d4:5e:60'),
(99, 'MSI', '00:11:09:0c:70:f7'),
(100, 'KMer-PK', '34:08:04:d4:05:3c'),
(101, 'steamlab-guest', '54:a0:50:d8:06:79'),
(102, 'steamlab', '54:a0:50:d8:06:78'),
(103, 'ktee1', '74:ea:3a:de:ae:1c'),
(104, 'ktee', '74:ea:3a:ae:9c:14'),
(105, 'eduroam', '00:c8:8b:e8:86:b2'),
(106, 'eduroam', '00:c8:8b:5b:bc:92'),
(107, 'FEI-FREE-1Mbit', '00:c8:8b:e8:86:b1'),
(108, 'eduroam', '00:42:68:9c:29:72'),
(109, 'FEI-FREE-1Mbit', '00:c8:8b:5b:bc:91'),
(110, 'FEI-FREE-1Mbit', '00:42:68:9c:29:71'),
(111, 'ktee3', '00:1b:fc:60:de:c6'),
(112, 'eduroam', '00:c8:8b:ab:9b:02'),
(113, 'FEI-FREE-1Mbit', '00:c8:8b:ab:9b:01'),
(114, 'FEI-FREE-1Mbit', '00:c8:8b:9c:7b:61'),
(115, 'FEI-FREE-1Mbit', '00:c8:8b:ab:59:31'),
(116, 'eduroam', '00:c8:8b:ab:59:32'),
(117, 'eduroam', '00:42:68:04:67:b2'),
(118, 'eduroam', '00:c8:8b:9c:7b:62'),
(119, 'OEMP', '00:0e:a6:26:83:54'),
(120, 'KEE7', '00:22:2d:03:cb:14'),
(121, 'D-Link DSL-2751', 'bc:f6:85:5b:36:d3'),
(122, 'Asus RT-N10', '48:5b:39:d7:33:0e'),
(123, 'c604', 'c4:e9:84:e5:b1:40'),
(124, 'NETGEAR_EXT', 'c4:04:15:91:88:aa'),
(125, 'MLgroup_guest', '16:fe:ed:89:21:4b'),
(126, 'ml_group2', '10:fe:ed:89:21:4b'),
(127, 'UEAE', '1c:87:2c:67:2e:00'),
(128, 'SinaY', 'f0:7d:68:46:ef:42'),
(129, 'C516', '64:66:b3:f7:97:78'),
(130, 'UEAE', '1c:87:2c:67:36:b8'),
(131, 'UEAE', '1c:b7:2c:d8:31:08'),
(132, 'samsung_print', 'ee:8e:37:14:b8:a1'),
(133, 'Hatapata-2p', 'c8:3a:35:40:c2:88'),
(134, 'UEAE-R', '5c:d9:98:6c:94:02'),
(135, 'Hatapata1', 'f8:d1:11:92:0a:0c'),
(136, 'FEI-FREE-1Mbit', '00:42:68:9c:71:f1'),
(137, 'eduroam', '00:42:68:9c:89:62'),
(138, 'eduroam', '00:42:68:9c:71:f2'),
(139, 'eduroam', '00:42:68:74:8c:32'),
(140, 'FEI-FREE-1Mbit', '00:42:68:9c:89:61'),
(141, 'FEI-FREE-1Mbit', '00:42:68:74:8c:31'),
(142, 'TP-LINK_ED0585', '74:ea:3a:ed:05:85'),
(143, 'eduroam', '00:42:68:80:0b:c2'),
(144, 'FEI-FREE-1Mbit', '00:42:68:80:0b:c1'),
(145, 'WIFI_HOME2', '84:16:f9:d4:b9:b2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answers`
--
ALTER TABLE `answers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `question_id` (`question_id`);

--
-- Indexes for table `competitions`
--
ALTER TABLE `competitions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `owner` (`owner`);

--
-- Indexes for table `competitors`
--
ALTER TABLE `competitors`
  ADD PRIMARY KEY (`id`),
  ADD KEY `competition_id` (`competition_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `measurement`
--
ALTER TABLE `measurement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `wifi_id` (`wifi_id`),
  ADD KEY `block_id` (`block_id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `competition_id` (`competition_id`),
  ADD KEY `location_id` (`location_id`);

--
-- Indexes for table `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`id`),
  ADD KEY `competitor_id` (`competitor_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wifi`
--
ALTER TABLE `wifi`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `answers`
--
ALTER TABLE `answers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `competitions`
--
ALTER TABLE `competitions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `competitors`
--
ALTER TABLE `competitors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `location`
--
ALTER TABLE `location`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
--
-- AUTO_INCREMENT for table `measurement`
--
ALTER TABLE `measurement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1001;
--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `score`
--
ALTER TABLE `score`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `wifi`
--
ALTER TABLE `wifi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=146;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `answers`
--
ALTER TABLE `answers`
  ADD CONSTRAINT `question-answer` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `competitions`
--
ALTER TABLE `competitions`
  ADD CONSTRAINT `user-competition` FOREIGN KEY (`owner`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `competitors`
--
ALTER TABLE `competitors`
  ADD CONSTRAINT `competition-competitor` FOREIGN KEY (`competition_id`) REFERENCES `competitions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `users-competitor` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `measurement`
--
ALTER TABLE `measurement`
  ADD CONSTRAINT `location-measurement` FOREIGN KEY (`block_id`) REFERENCES `location` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `wifi-measurement` FOREIGN KEY (`wifi_id`) REFERENCES `wifi` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `competition-question` FOREIGN KEY (`competition_id`) REFERENCES `competitions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `location-question` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `score`
--
ALTER TABLE `score`
  ADD CONSTRAINT `competitor-score` FOREIGN KEY (`competitor_id`) REFERENCES `competitors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
