-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: localhost
-- Üretim Zamanı: 12 Kas 2023, 19:55:19
-- Sunucu sürümü: 8.0.31
-- PHP Sürümü: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `acenta`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `otel`
--

CREATE TABLE `otel` (
  `id` int NOT NULL,
  `name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `star` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `property` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mail` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `otel`
--

INSERT INTO `otel` (`id`, `name`, `star`, `property`, `address`, `phone`, `mail`) VALUES
(18, 'Hilton Otel', '*****', 'Otopark\nKapalı Havuz\nSPA\n', 'İstanbul', '216 222 22 22', 'hilton@otel.com'),
(19, 'Grand Rixos Otel', '*****', 'Otopark\nSPA\nKapalı Havuz', 'Antalya', '321 333 33 33', 'grand@rixos.com'),
(20, 'Ankara Palas', '*****', 'Otopark\nKapalı Havuz', 'Ankara', '312 555 55 55', 'ankara@palas.com'),
(21, 'Adıyaman Anadolu Otel', '*****', 'Otopark', 'Adıyaman', '859 999 99 99', 'adıyaman@anadolu.com'),
(22, 'GreenGarden', '****', 'Otopark\nAquapark\nKapalı Havuz \nSauna\nSPA', 'Bolu', '222 222 22 22', 'green@garden.com'),
(23, 'Ankara Ankara Oteli', '***', 'Otopark\nKapalı havuz', 'Ankara', '231 568 66 66', 'ankara@ankara');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `reservation_info`
--

CREATE TABLE `reservation_info` (
  `id` int NOT NULL,
  `client_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `client_phone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `client_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `client_note` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `room_id` int NOT NULL,
  `check_in` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `check_out` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `adult_num` int NOT NULL,
  `child_num` int NOT NULL,
  `total_price` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `reservation_info`
--

INSERT INTO `reservation_info` (`id`, `client_name`, `client_phone`, `client_email`, `client_note`, `room_id`, `check_in`, `check_out`, `adult_num`, `child_num`, `total_price`) VALUES
(4, 'Ömer Faruk ŞEN', '555 555 55 55', 'omerfaruk@sen.com', 'Bir kaç gece daha uzatılabilir', 12, '03/02/2023', '06/02/2023', 1, 0, 4500),
(5, 'Haticenur DincelŞEN', '666 666 66 66', 'hn_hn@hn.com', 'Tbmm manzaralı', 18, '06/06/2023', '10/06/2023', 2, 0, 10400),
(6, 'Ahmet Efendioğlu', '0555 555 55 55', 'ahmetefendioglu@gmail.com', 'Not yok.', 19, '06/06/2023', '10/06/2023', 1, 0, 6000),
(7, 'Ahmet Ahmetoğlu', '5658658658', 'ahmet@ahmetoğlu', 'nnot yok', 21, '06/06/2023', '10/06/2023', 1, 0, 4000);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `room`
--

CREATE TABLE `room` (
  `id` int NOT NULL,
  `room_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `stock` int NOT NULL,
  `season_id` int NOT NULL,
  `adult_price` int NOT NULL,
  `child_price` int NOT NULL,
  `otel_type_id` int NOT NULL,
  `otel_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `room`
--

INSERT INTO `room` (`id`, `room_type`, `stock`, `season_id`, `adult_price`, `child_price`, `otel_type_id`, `otel_id`) VALUES
(12, 'double', 9, 15, 1500, 1000, 24, 18),
(13, 'single', 10, 16, 1250, 850, 25, 18),
(14, 'double', 10, 17, 1200, 650, 29, 19),
(15, 'single', 10, 18, 2500, 2000, 27, 19),
(16, 'single', 10, 21, 750, 500, 34, 21),
(17, 'double', 5, 22, 500, 350, 35, 21),
(18, 'double', 9, 20, 1300, 1000, 30, 20),
(19, 'double', 9, 24, 1500, 1000, 37, 22),
(20, 'single', 5, 24, 1000, 750, 39, 22),
(21, 'single', 4, 26, 1000, 750, 41, 23);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `room_property`
--

CREATE TABLE `room_property` (
  `id` int NOT NULL,
  `property` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `room_id` int NOT NULL,
  `bed` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `area` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `room_property`
--

INSERT INTO `room_property` (`id`, `property`, `room_id`, `bed`, `area`) VALUES
(4, 'Televizyon \nMinibar \nKasa', 12, '2', 35),
(5, 'Televizyon \nMinibar ', 13, '1', 25),
(6, 'Televizyon \nMinibar ', 14, '1', 25),
(7, 'Televizyon \nMinibar ', 15, '1', 25),
(8, 'Televizyon \nMinibar ', 16, '2', 20),
(9, 'Televizyon ', 17, '2', 20),
(10, 'Televizyon \nMinibar ', 18, '1', 30),
(11, 'Televizyon \nMinibar \nOyun Konsolu\nKasa', 19, '2', 35),
(12, 'Televizyon \nMinibar ', 20, '1', 25),
(13, 'Televizyon \nMinibar ', 21, '1', 25);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `season`
--

CREATE TABLE `season` (
  `id` int NOT NULL,
  `season_start` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `season_end` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `otel_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `season`
--

INSERT INTO `season` (`id`, `season_start`, `season_end`, `otel_id`) VALUES
(15, '01/01/2023', '31/05/2023', 18),
(16, '01/06/2023', '31/12/2023', 18),
(17, '01/01/2023', '31/05/2023', 19),
(18, '01/06/2023', '31/12/2023', 19),
(19, '01/01/2023', '31/05/2023', 20),
(20, '01/06/2023', '31/12/2023', 20),
(21, '01/01/2023', '31/05/2023', 21),
(22, '01/06/2023', '31/12/2023', 21),
(23, '01/01/2023', '31/05/2023', 22),
(24, '01/06/2023', '31/12/2023', 22),
(25, '01/01/2023', '31/05/2023', 23),
(26, '01/06/2023', '31/12/2023', 23);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `type_otel`
--

CREATE TABLE `type_otel` (
  `id` int NOT NULL,
  `type` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `otel_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `type_otel`
--

INSERT INTO `type_otel` (`id`, `type`, `otel_id`) VALUES
(24, 'Ultra Herşey Dahil', 18),
(25, 'Herşey Dahil', 18),
(26, 'Tam Pansiyon', 18),
(27, 'Ultra Herşey Dahil', 19),
(28, 'Herşey Dahil', 19),
(29, 'Tam Pansiyon', 19),
(30, 'Herşey Dahil', 20),
(31, 'Tam Pansiyon', 20),
(32, 'Yarım Pansiyon', 20),
(33, 'Oda Kahvaltı', 20),
(34, 'Yarım Pansiyon', 21),
(35, 'Oda Kahvaltı', 21),
(36, 'Sadece Yatak', 21),
(37, 'Ultra Herşey Dahil', 22),
(38, 'Herşey Dahil', 22),
(39, 'Tam Pansiyon', 22),
(40, 'Herşey Dahil', 23),
(41, 'Tam Pansiyon', 23),
(42, 'Yarım Pansiyon', 23);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `user`
--

CREATE TABLE `user` (
  `id` int NOT NULL,
  `name` varchar(250) COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(250) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(250) COLLATE utf8mb4_general_ci NOT NULL,
  `type` enum('Admin','Employee') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `user`
--

INSERT INTO `user` (`id`, `name`, `username`, `password`, `type`) VALUES
(1, 'Ömer Faruk', 'omerfaruk', '1234', 'Admin'),
(2, 'Emin Demirci', 'emindemirci', '1234', 'Employee'),
(10, 'Ferit Aşçı', 'feritascı', '1234', 'Employee'),
(11, 'ahmet', 'ahmet', '1234', 'Employee');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `otel`
--
ALTER TABLE `otel`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `reservation_info`
--
ALTER TABLE `reservation_info`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `room_property`
--
ALTER TABLE `room_property`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `season`
--
ALTER TABLE `season`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `type_otel`
--
ALTER TABLE `type_otel`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `otel`
--
ALTER TABLE `otel`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Tablo için AUTO_INCREMENT değeri `reservation_info`
--
ALTER TABLE `reservation_info`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Tablo için AUTO_INCREMENT değeri `room`
--
ALTER TABLE `room`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Tablo için AUTO_INCREMENT değeri `room_property`
--
ALTER TABLE `room_property`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Tablo için AUTO_INCREMENT değeri `season`
--
ALTER TABLE `season`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Tablo için AUTO_INCREMENT değeri `type_otel`
--
ALTER TABLE `type_otel`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- Tablo için AUTO_INCREMENT değeri `user`
--
ALTER TABLE `user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
