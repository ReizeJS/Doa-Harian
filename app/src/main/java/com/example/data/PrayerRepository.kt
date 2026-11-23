package com.example.data

import com.example.model.ContextualRecommendation
import com.example.model.MemorizationTip
import com.example.model.Prayer
import com.example.model.PrayerCategory
import java.util.Calendar
import java.util.Locale

object PrayerRepository {

  val prayers: List<Prayer> =
    listOf(
      // ==========================================
      // KATEGORI 1: AKTIVITAS HARIAN (12 DOA)
      // ==========================================
      Prayer(
        id = "bangun_tidur",
        title = "Doa Bangun Tidur",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Bukhari no. 6312",
        contextHint = "Dibaca saat baru terjaga di pagi hari",
        arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ",
        latin = "Alhamdulillaahilladzii ahyaanaa ba‘da maa amaatanaa wa ilaihin nusyuur.",
        translation =
          "Segala puji bagi Allah yang telah menghidupkan kami kembali setelah mematikan kami, dan hanya kepada-Nya kami kembali.",
        notes = "Membantu mengawali hari dengan rasa syukur sebelum beraktivitas.",
        keywords = listOf("pagi", "bangun", "subuh", "tidur", "syukur", "terjaga"),
      ),
      Prayer(
        id = "sebelum_tidur",
        title = "Doa Sebelum Tidur",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Bukhari no. 6324",
        contextHint = "Dibaca sebelum memejamkan mata untuk beristirahat",
        arabic = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
        latin = "Bismikallaahumma amuutu wa ahyaa.",
        translation =
          "Dengan nama-Mu ya Allah, aku mati (tidur) dan aku hidup (bangun kembali).",
        notes = "Ringkas dan mudah diamalkan setiap malam.",
        keywords = listOf("tidur", "malam", "istirahat", "bismika", "kasur"),
      ),
      Prayer(
        id = "terbangun_gelisah",
        title = "Doa Terbangun di Malam Hari Karena Gelisah",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Abu Dawud no. 3893 & Tirmidzi",
        contextHint = "Dibaca saat terbangun tiba-tiba dari tidur atau terkejut",
        arabic =
          "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ غَضَبِهِ وَعِقَابِهِ وَشَرِّ عِبَادِهِ، وَمِنْ هَمَزَاتِ الشَّيَاطِينِ وَأَنْ يَحْضُرُونِ",
        latin =
          "A‘uudzu bikalimaatillaahit-taammaati min ghadhabihi wa ‘iqaabihi wa syarri ‘ibaadih, wa min hamazaatisy-syayaathiini wa an yahdhuruun.",
        translation =
          "Aku berlindung dengan kalimat-kalimat Allah yang sempurna dari murka-Nya, siksa-Nya, kejahatan hamba-hamba-Nya, serta dari bisikan setan dan kedatangan mereka kepadaku.",
        notes =
          "Dapat dibaca ketika terbangun karena mimpi buruk atau rasa cemas di malam hari.",
        keywords = listOf("mimpi buruk", "gelisah", "kaget", "terbangun", "malam", "takut"),
      ),
      Prayer(
        id = "mimpi_baik",
        title = "Doa Ketika Mendapat Mimpi Baik",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Bukhari no. 6985",
        contextHint = "Dibaca setelah bermimpi indah atau menenangkan",
        arabic = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
        latin = "Alhamdulillaahi rabbil ‘aalamiin.",
        translation = "Segala puji bagi Allah, Tuhan seluruh alam.",
        notes =
          "Mimpi yang baik berasal dari Allah; syukuri dan ceritakan hanya kepada orang yang dicintai.",
        keywords = listOf("mimpi", "baik", "indah", "syukur", "alhamdulillah"),
      ),
      Prayer(
        id = "sebelum_makan",
        title = "Doa Sebelum Makan",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Ibnu Sunni",
        contextHint = "Dibaca sebelum menyantap makanan atau minuman",
        arabic = "اللَّهُمَّ بَارِكْ لَنَا فِيمَا رَزَقْتَنَا وَقِنَا عَذَابَ النَّارِ، بِسْمِ اللَّهِ",
        latin = "Allaahumma baarik lanaa fiimaa razaqtanaa wa qinaa ‘adzaaban naar. Bismillaah.",
        translation =
          "Ya Allah, berkahilah kami dalam rezeki yang Engkau berikan kepada kami dan peliharalah kami dari siksa neraka. Dengan nama Allah.",
        notes = "Membaca Bismillah menjadi pembuka keberkahan dalam makanan.",
        keywords = listOf("makan", "minum", "sarapan", "makan siang", "kantin", "rezeki"),
      ),
      Prayer(
        id = "lupa_bismillah_makan",
        title = "Doa Ketika Lupa Membaca Bismillah Saat Makan",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Abu Dawud no. 3767 & Tirmidzi",
        contextHint = "Dibaca jika baru teringat di tengah-tengah makan",
        arabic = "بِسْمِ اللَّهِ أَوَّلَهُ وَآخِرَهُ",
        latin = "Bismillaahi awwalahu wa aakhirahu.",
        translation = "Dengan nama Allah pada awal dan akhirnya.",
        notes = "Ucapkan begitu teringat meskipun sudah hampir selesai makan.",
        keywords = listOf("lupa", "makan", "bismillah", "tengah makan"),
      ),
      Prayer(
        id = "sesudah_makan",
        title = "Doa Sesudah Makan",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Abu Dawud & Tirmidzi",
        contextHint = "Dibaca setelah selesai makan atau minum",
        arabic =
          "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنِي هَذَا وَرَزَقَنِيهِ مِنْ غَيْرِ حَوْلٍ مِنِّي وَلَا قُوَّةٍ",
        latin =
          "Alhamdulillaahilladzii ath‘amanii haadzaa wa razaqaniihi min ghairi haulin minnii wa laa quwwah.",
        translation =
          "Segala puji bagi Allah yang telah memberi makan ini kepadaku dan mengaruniakannya tanpa daya dan kekuatan dariku.",
        notes = "Bentuk syukur sederhana atas kecukupan rezeki setiap hari.",
        keywords = listOf("selesai makan", "sesudah makan", "kenyang", "syukur"),
      ),
      Prayer(
        id = "masuk_kamar_mandi",
        title = "Doa Masuk Kamar Mandi",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Bukhari no. 142 & Muslim",
        contextHint = "Dibaca sebelum melangkah masuk ke kamar mandi atau toilet",
        arabic = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْخُبُثِ وَالْخَبَائِثِ",
        latin = "Allaahumma innii a‘uudzu bika minal khubutsi wal khabaa-its.",
        translation =
          "Ya Allah, sesungguhnya aku berlindung kepada-Mu dari godaan setan laki-laki dan setan perempuan.",
        notes = "Dahulukan kaki kiri saat masuk kamar mandi.",
        keywords = listOf("wc", "toilet", "kamar mandi", "mandi", "masuk"),
      ),
      Prayer(
        id = "keluar_kamar_mandi",
        title = "Doa Keluar Kamar Mandi",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Abu Dawud & Tirmidzi",
        contextHint = "Dibaca setelah keluar dari kamar mandi",
        arabic = "غُفْرَانَكَ",
        latin = "Ghufraanak.",
        translation = "Aku memohon ampunan-Mu ya Allah.",
        notes = "Dahulukan kaki kanan saat melangkah keluar.",
        keywords = listOf("wc", "toilet", "kamar mandi", "keluar", "ghufranaka"),
      ),
      Prayer(
        id = "mengenakan_pakaian",
        title = "Doa Mengenakan Pakaian",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Abu Dawud no. 4023 & Tirmidzi",
        contextHint = "Dibaca saat memakai baju, jaket, atau almamater",
        arabic =
          "الْحَمْدُ لِلَّهِ الَّذِي كَسَانِي هَذَا الثَّوْبَ وَرَزَقَنِيهِ مِنْ غَيْرِ حَوْلٍ مِنِّي وَلَا قُوَّةٍ",
        latin =
          "Alhamdulillaahilladzii kasaanii haadzats-tsauba wa razaqaniihi min ghairi haulin minnii wa laa quwwah.",
        translation =
          "Segala puji bagi Allah yang telah memakaikan pakaian ini kepadaku dan mengaruniakannya tanpa daya dan kekuatan dariku.",
        notes = "Mulailah mengenakan pakaian dari sisi kanan terlebih dahulu.",
        keywords = listOf("baju", "pakaian", "jaket", "almamater", "pakai"),
      ),
      Prayer(
        id = "melepas_pakaian",
        title = "Doa Melepas Pakaian",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Ibnu Sunni",
        contextHint = "Dibaca saat hendak berganti pakaian atau sebelum mandi",
        arabic = "بِسْمِ اللَّهِ الَّذِي لَا إِلَهَ إِلَّا هُوَ",
        latin = "Bismillaahilladzii laa ilaaha illaa huwa.",
        translation = "Dengan nama Allah yang tiada Tuhan selain Dia.",
        notes = "Menjadi penutup dan pelindung diri ketika berganti pakaian.",
        keywords = listOf("lepas", "ganti baju", "pakaian", "mandi"),
      ),
      Prayer(
        id = "bercermin",
        title = "Doa Ketika Bercermin",
        category = PrayerCategory.AKTIVITAS_HARIAN,
        source = "HR. Ahmad",
        contextHint = "Dibaca saat merapikan diri sebelum berangkat beraktivitas",
        arabic = "اللَّهُمَّ كَمَا حَسَّنْتَ خَلْقِي فَحَسِّنْ خُلُقِي",
        latin = "Allaahumma kamaa hassanta khalqii fa hassin khuluqii.",
        translation =
          "Ya Allah, sebagaimana Engkau telah memperindah rupaku, maka perindahlah pula akhlakku.",
        notes = "Pengingat untuk menjaga penampilan sekaligus adab dalam bergaul.",
        keywords = listOf("cermin", "kaca", "bercermin", "akhlak", "rapi"),
      ),

      // ==========================================
      // KATEGORI 2: RUMAH & PERJALANAN (11 DOA)
      // ==========================================
      Prayer(
        id = "keluar_rumah",
        title = "Doa Keluar Rumah / Kos",
        category = PrayerCategory.RUMAH_PERJALANAN,
        source = "HR. Abu Dawud & Tirmidzi",
        contextHint = "Dibaca saat melangkah keluar pintu rumah atau kos",
        arabic = "بِسْمِ اللَّهِ تَوَكَّلْتُ عَلَى اللَّهِ، لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ",
        latin = "Bismillaahi tawakkaltu ‘alallaahi, laa haula wa laa quwwata illaa billaah.",
        translation =
          "Dengan nama Allah, aku bertawakal kepada Allah. Tiada daya dan tiada kekuatan kecuali dengan pertolongan Allah.",
        notes = "Mengiringi setiap langkah keluar rumah agar senantiasa dalam penjagaan-Nya.",
        keywords = listOf("keluar", "kos", "rumah", "berangkat", "kampus", "tawakal"),
      ),
      Prayer(
        id = "masuk_rumah",
        title = "Doa Masuk Rumah / Kos",
        category = PrayerCategory.RUMAH_PERJALANAN,
        source = "HR. Abu Dawud no. 5096",
        contextHint = "Dibaca ketika pulang dan membuka pintu rumah atau kos",
        arabic = "بِسْمِ اللَّهِ وَلَجْنَا، وَبِسْمِ اللَّهِ خَرَجْنَا، وَعَلَى رَبِّنَا تَوَكَّلْنَا",
        latin = "Bismillaahi walajnaa, wa bismillaahi kharajnaa, wa ‘alaa rabbinaa tawakkalnaa.",
        translation =
          "Dengan nama Allah kami masuk, dengan nama Allah kami keluar, dan hanya kepada Tuhan kami, kami bertawakal.",
        notes = "Sertai dengan salam ketika masuk ke dalam rumah atau kamar kos.",
        keywords = listOf("masuk", "pulang", "kos", "rumah", "pintu", "salam"),
      ),
      Prayer(
        id = "naik_kendaraan",
        title = "Doa Naik Kendaraan",
        category = PrayerCategory.RUMAH_PERJALANAN,
        source = "QS. Az-Zukhruf: 13–14",
        contextHint = "Dibaca saat naik motor, mobil, bus, kereta, atau kendaraan umum",
        arabic =
          "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَذَا وَمَا كُنَّا لَهُ مُقْرِنِينَ، وَإِنَّا إِلَى رَبِّنَا لَمُنْقَلِبُونَ",
        latin =
          "Subhaanalladzii sakhkhara lanaa haadzaa wa maa kunnaa lahu muqriniin, wa innaa ilaa rabbinaa lamunqalibuun.",
        translation =
          "Mahasuci Allah yang telah menundukkan kendaraan ini bagi kami, padahal sebelumnya kami tidak mampu menguasainya. Dan sesungguhnya kami akan kembali kepada Tuhan kami.",
        notes = "Biasakan membaca sebelum menyalakan kendaraan atau saat perjalanan dimulai.",
        keywords = listOf("motor", "mobil", "bus", "kereta", "kendaraan", "ojek", "jalan"),
      ),
      Prayer(
        id = "perjalanan_safar",
        title = "Doa Perjalanan Jauh (Safar)",
        category = PrayerCategory.RUMAH_PERJALANAN,
        source = "HR. Muslim no. 1342",
        contextHint = "Dibaca saat mudik, KKN, studi lapangan, atau bepergian jauh",
        arabic =
          "اللَّهُمَّ هَوِّنْ عَلَيْنَا سَفَرَنَا هَذَا وَاطْوِ عَنَّا بُعْدَهُ، اللَّهُمَّ أَنْتَ الصَّاحِبُ فِي السَّفَرِ وَالْخَلِيفَةُ فِي الْأَهْلِ",
        latin =
          "Allaahumma hawwin ‘alainaa safaranaa haadzaa wathwi ‘annaa bu‘dah, Allaahumma antash-shaahibu fis-safari wal khaliifatu fil-ahl.",
        translation =
          "Ya Allah, mudahkanlah perjalanan kami ini dan dekatkanlah jaraknya bagi kami. Ya Allah, Engkaulah pendamping dalam perjalanan dan pelindung bagi keluarga yang kami tinggalkan.",
        notes = "Sangat dianjurkan ketika menempuh perjalanan luar kota atau pulang kampung.",
        keywords = listOf("safar", "mudik", "kkn", "bepergian", "perjalanan jauh", "pesawat"),
      ),
      Prayer(
        id = "masuk_daerah_baru",
        title = "Doa Memasuki Kota atau Tempat Baru",
        category = PrayerCategory.RUMAH_PERJALANAN,
        source = "HR. An-Nasa'i & Ibnu Sunni",
        contextHint = "Dibaca saat tiba di kota rantau, kampus baru, atau lokasi KKN",
        arabic =
          "اللَّهُمَّ إِنِّي أَسْأَلُكَ خَيْرَهَا وَخَيْرَ أَهْلِهَا وَخَيْرَ مَا فِيهَا، وَأَعُوذُ بِكَ مِنْ شَرِّهَا وَشَرِّ أَهْلِهَا وَشَرِّ مَا فِيهَا",
        latin =
          "Allaahumma innii as-aluka khairahaa wa khaira ahlihaa wa khaira maa fiihaa, wa a‘uudzu bika min syarrihaa wa syarri ahlihaa wa syarri maa fiihaa.",
        translation =
          "Ya Allah, aku memohon kepada-Mu kebaikan negeri ini, kebaikan penduduknya, dan kebaikan yang ada di dalamnya. Dan aku berlindung kepada-Mu dari keburukannya, keburukan penduduknya, dan keburukan yang ada di dalamnya.",
        notes = "Membawa ketenangan batin saat beradaptasi di lingkungan baru.",
        keywords = listOf("kota", "rantau", "tempat baru", "kampus", "desa", "kkn"),
      ),
      Prayer(
        id = "pulang_safar",
        title = "Doa Kembali dari Perjalanan",
        category = PrayerCategory.RUMAH_PERJALANAN,
        source = "HR. Bukhari no. 1797 & Muslim",
        contextHint = "Dibaca saat tiba kembali di rumah atau kos dengan selamat",
        arabic = "آيِبُونَ تَائِبُونَ عَابِدُونَ لِرَبِّنَا حَامِدُونَ",
        latin = "Aayibuuna taa-ibuuna ‘aabiduuna lirabbinaa haamiduun.",
        translation =
          "Kami kembali dengan selamat, bertaubat, beribadah, dan memuji Tuhan kami.",
        notes = "Ucapkan sebagai bentuk syukur atas keselamatan selama perjalanan pulang.",
        keywords = listOf("kembali", "pulang", "selamat", "tiba", "safar"),
      ),
      Prayer(
        id = "ketika_hujan",
        title = "Doa Ketika Hujan Turun",
        category = PrayerCategory.RUMAH_PERJALANAN,
        source = "HR. Bukhari no. 1032",
        contextHint = "Dibaca saat hujan mulai turun sebagai waktu mustajab berdoa",
        arabic = "اللَّهُمَّ صَيِّبًا نَافِعًا",
        latin = "Allaahumma shayyiban naafi‘aa.",
        translation = "Ya Allah, turunkanlah hujan yang penuh manfaat dan keberkahan.",
        notes = "Waktu turunnya hujan termasuk salah satu momen mustajab untuk memanjatkan doa.",
        keywords = listOf("hujan", "gerimis", "cuaca", "berkah", "mustajab"),
      ),
      Prayer(
        id = "setelah_hujan",
        title = "Doa Setelah Hujan Reda",
        category = PrayerCategory.RUMAH_PERJALANAN,
        source = "HR. Bukhari no. 846 & Muslim",
        contextHint = "Dibaca ketika hujan telah berhenti",
        arabic = "مُطِرْنَا بِفَضْلِ اللَّهِ وَرَحْمَتِهِ",
        latin = "Muthirnaa bifadhlillaahi wa rahmatih.",
        translation = "Kami diberi hujan karena karunia Allah dan rahmat-Nya.",
        notes = "Mengakui bahwa segala nikmat alam semata-mata berasal dari rahmat Allah.",
        keywords = listOf("hujan reda", "berhenti hujan", "rahmat", "syukur"),
      ),
      Prayer(
        id = "mendengar_petir",
        title = "Doa Ketika Mendengar Petir",
        category = PrayerCategory.RUMAH_PERJALANAN,
        source = "HR. Malik & Bukhari (Adabul Mufrad)",
        contextHint = "Dibaca saat terdengar suara petir atau guntur menggelegar",
        arabic = "سُبْحَانَ الَّذِي يُسَبِّحُ الرَّعْدُ بِحَمْدِهِ وَالْمَلَائِكَةُ مِنْ خِيفَتِهِ",
        latin = "Subhaanalladzii yusabbihur-ra‘du bihamdihi wal malaa-ikatu min khiifatih.",
        translation =
          "Mahasuci Allah yang petir bertasbih dengan memuji-Nya dan para malaikat pun bertasbih karena takut kepada-Nya.",
        notes = "Menenangkan hati ketika cuaca buruk atau badai.",
        keywords = listOf("petir", "guntur", "kilat", "badai", "hujan deras"),
      ),
      Prayer(
        id = "singgah_tempat",
        title = "Doa Ketika Singgah di Suatu Tempat",
        category = PrayerCategory.RUMAH_PERJALANAN,
        source = "HR. Muslim no. 2708",
        contextHint = "Dibaca saat singgah di penginapan, rest area, atau posko kegiatan",
        arabic = "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ",
        latin = "A‘uudzu bikalimaatillaahit-taammaati min syarri maa khalaq.",
        translation =
          "Aku berlindung dengan kalimat-kalimat Allah yang sempurna dari kejahatan makhluk yang Dia ciptakan.",
        notes =
          "Rasulullah mengabarkan siapa yang membacanya saat singgah, tidak ada sesuatu pun yang membahayakannya hingga ia pergi.",
        keywords = listOf("singgah", "hotel", "penginapan", "posko", "istirahat", "perlindungan"),
      ),
      Prayer(
        id = "keberkahan_tempat",
        title = "Doa Memohon Keberkahan Tempat Tinggal",
        category = PrayerCategory.RUMAH_PERJALANAN,
        source = "QS. Al-Mu’minun: 29",
        contextHint = "Dibaca saat menempati kamar kos, kontrakan, atau rumah baru",
        arabic = "رَبِّ أَنْزِلْنِي مُنْزَلًا مُبَارَكًا وَأَنْتَ خَيْرُ الْمُنْزِلِينَ",
        latin = "Rabbi anzilnii munzalan mubaarakan wa anta khairul munziliin.",
        translation =
          "Ya Tuhanku, tempatkanlah aku pada tempat yang penuh berkah, dan Engkau adalah sebaik-baik pemberi tempat.",
        notes = "Baik diamalkan saat pindahan kos atau memulai masa tinggal di tempat baru.",
        keywords = listOf("kos baru", "pindah", "kontrakan", "rumah baru", "berkah"),
      ),

      // ==========================================
      // KATEGORI 3: KULIAH & ILMU (10 DOA)
      // ==========================================
      Prayer(
        id = "sebelum_belajar",
        title = "Doa Sebelum Belajar",
        category = PrayerCategory.KULIAH_ILMU,
        source = "QS. Thaha: 114 & Doa Ulama",
        contextHint = "Dibaca sebelum memulai kuliah, membaca materi, atau mengerjakan tugas",
        arabic = "رَبِّ زِدْنِي عِلْمًا، وَارْزُقْنِي فَهْمًا",
        latin = "Rabbi zidnii ‘ilmaa, warzuqnii fahmaa.",
        translation =
          "Ya Tuhanku, tambahkanlah ilmu kepadaku dan karuniakanlah kepadaku pemahaman yang baik.",
        notes = "Singkat, padat, dan menjaga fokus niat menuntut ilmu karena Allah.",
        keywords = listOf("belajar", "kuliah", "tugas", "skripsi", "ilmu", "paham", "kampus"),
      ),
      Prayer(
        id = "ilmu_bermanfaat",
        title = "Doa Memohon Ilmu Bermanfaat",
        category = PrayerCategory.KULIAH_ILMU,
        source = "HR. Ibnu Majah no. 925",
        contextHint = "Dibaca di pagi hari sebelum beraktivitas agar ilmu dan amal diberkahi",
        arabic =
          "اللَّهُمَّ إِنِّي أَسْأَلُكَ عِلْمًا نَافِعًا، وَرِزْقًا طَيِّبًا، وَعَمَلًا مُتَقَبَّلًا",
        latin =
          "Allaahumma innii as-aluka ‘ilman naafi‘an, wa rizqan thayyiban, wa ‘amalan mutaqabbalaa.",
        translation =
          "Ya Allah, sesungguhnya aku memohon kepada-Mu ilmu yang bermanfaat, rezeki yang baik, dan amal yang diterima.",
        notes = "Merupakan doa rutin Nabi Muhammad setiap selesai shalat Subuh.",
        keywords = listOf("pagi", "ilmu bermanfaat", "rezeki", "amal", "subuh", "kuliah"),
      ),
      Prayer(
        id = "kelancaran_lisan",
        title = "Doa Kelancaran Lisan & Urusan",
        category = PrayerCategory.KULIAH_ILMU,
        source = "QS. Thaha: 25–28",
        contextHint = "Dibaca sebelum presentasi, sidang, seminar, atau diskusi kelas",
        arabic =
          "رَبِّ اشْرَحْ لِي صَدْرِي، وَيَسِّرْ لِي أَمْرِي، وَاحْلُلْ عُقْدَةً مِنْ لِسَانِي، يَفْقَهُوا قَوْلِي",
        latin =
          "Rabbisyrah lii shadrii, wa yassir lii amrii, wahlul ‘uqdatan min lisaanii, yafqahuu qaulii.",
        translation =
          "Ya Tuhanku, lapangkanlah dadaku, mudahkanlah urusanku, dan lepaskanlah kekakuan dari lidahku agar mereka memahami perkataanku.",
        notes = "Doa Nabi Musa saat menghadapi tugas berat; sangat membantu meredakan gugup bicara.",
        keywords = listOf("presentasi", "sidang", "seminar", "lisan", "bicara", "diskusi", "gugup"),
      ),
      Prayer(
        id = "setelah_belajar",
        title = "Doa Setelah Belajar Agar Tidak Lupa",
        category = PrayerCategory.KULIAH_ILMU,
        source = "Doa Imam Al-Ghazali / Ulama",
        contextHint = "Dibaca usai kuliah atau belajar mandiri agar ingatan materi terjaga",
        arabic =
          "اللَّهُمَّ إِنِّي أَسْتَوْدِعُكَ مَا عَلَّمْتَنِيهِ فَارْدُدْهُ إِلَيَّ عِنْدَ حَاجَتِي إِلَيْهِ، وَلَا تَنْسَنِيهِ يَا رَبَّ الْعَالَمِينَ",
        latin =
          "Allaahumma innii astaudi‘uka maa ‘allamtaniiihi fardud-hu ilayya ‘inda haajatii ilaih, wa laa tansaniihi yaa rabbal ‘aalamiin.",
        translation =
          "Ya Allah, sesungguhnya aku menitipkan kepada-Mu apa yang telah Engkau ajarkan kepadaku, maka kembalikanlah ia kepadaku saat aku membutuhkannya, dan janganlah Engkau membuatku lupa, wahai Tuhan semesta alam.",
        notes = "Cocok dibaca di malam hari setelah mengulang catatan kuliah atau latihan soal.",
        keywords = listOf("selesai belajar", "ingatan", "lupa", "hafalan", "evaluasi", "ujian"),
      ),
      Prayer(
        id = "dimudahkan_urusan",
        title = "Doa Dimudahkan Segala Urusan",
        category = PrayerCategory.KULIAH_ILMU,
        source = "HR. Ibnu Hibban no. 974",
        contextHint = "Dibaca saat mengerjakan tugas sulit, revisi, atau urusan administrasi",
        arabic = "اللَّهُمَّ لَا سَهْلَ إِلَّا مَا جَعَلْتَهُ سَهْلًا، وَأَنْتَ تَجْعَلُ الْحَزْنَ إِذَا شِئْتَ سَهْلًا",
        latin =
          "Allaahumma laa sahla illaa maa ja‘altahu sahlaa, wa anta taj‘alul hazna idzaa syi’ta sahlaa.",
        translation =
          "Ya Allah, tidak ada kemudahan kecuali yang Engkau jadikan mudah, dan Engkau menjadikan kesulitan itu mudah apabila Engkau menghendakinya.",
        notes = "Mengingatkan diri untuk tetap tenang ketika menghadapi kendala akademik atau pekerjaan.",
        keywords = listOf("sulit", "mudah", "urusan", "revisi", "tugas", "ujian", "praktikum"),
      ),
      Prayer(
        id = "memohon_kebijaksanaan",
        title = "Doa Memohon Petunjuk & Ketepatan Berpikir",
        category = PrayerCategory.KULIAH_ILMU,
        source = "HR. Tirmidzi no. 3483",
        contextHint = "Dibaca saat harus mengambil keputusan atau menyelesaikan masalah kompleks",
        arabic = "اللَّهُمَّ أَلْهِمْنِي رُشْدِي، وَأَعِذْنِي مِنْ شَرِّ نَفْسِي",
        latin = "Allaahumma alhimnii rusydii, wa a‘idznii min syarri nafsii.",
        translation =
          "Ya Allah, ilhamkanlah kepadaku petunjuk yang lurus dan lindungilah aku dari keburukan hawa nafsu diriku sendiri.",
        notes = "Membantu kejernihan pikiran sebelum menentukan pilihan penting.",
        keywords = listOf("pikiran", "keputusan", "jernih", "bijak", "skripsi", "fokus"),
      ),
      Prayer(
        id = "lindung_ilmu_tak_manfaat",
        title = "Doa Berlindung dari Ilmu yang Tidak Bermanfaat",
        category = PrayerCategory.KULIAH_ILMU,
        source = "HR. Muslim no. 2722",
        contextHint = "Dibaca untuk menjaga keikhlasan dan manfaat ilmu yang dipelajari",
        arabic =
          "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ عِلْمٍ لَا يَنْفَعُ، وَمِنْ قَلْبٍ لَا يَخْشَعُ، وَمِنْ نَفْسٍ لَا تَشْبَعُ، وَمِنْ دَعْوَةٍ لَا يُسْتَجَابُ لَهَا",
        latin =
          "Allaahumma innii a‘uudzu bika min ‘ilmin laa yanfa‘, wa min qalbin laa yakhsya‘, wa min nafsin laa tasyba‘, wa min da‘watin laa yustajaabu lahaa.",
        translation =
          "Ya Allah, aku berlindung kepada-Mu dari ilmu yang tidak bermanfaat, dari hati yang tidak khusyuk, dari jiwa yang tidak pernah puas, dan dari doa yang tidak dikabulkan.",
        notes = "Menjaga agar ilmu yang dikejar membawa kebaikan bagi diri dan masyarakat.",
        keywords = listOf("ikhlas", "ilmu", "hati", "khusyuk", "doa"),
      ),
      Prayer(
        id = "menghadapi_ujian",
        title = "Doa Saat Menghadapi Kesulitan & Ujian",
        category = PrayerCategory.KULIAH_ILMU,
        source = "QS. Al-Anbiya: 87",
        contextHint = "Dibaca ketika merasa tertekan menghadapi ujian, tenggat, atau masalah berat",
        arabic = "لَا إِلَهَ إِلَّا أَنْتَ سُبْحَانَكَ إِنِّي كُنْتُ مِنَ الظَّالِمِينَ",
        latin = "Laa ilaaha illaa anta subhaanaka innii kuntu minazh-zhaalimiin.",
        translation =
          "Tidak ada Tuhan selain Engkau, Mahasuci Engkau, sesungguhnya aku termasuk orang-orang yang zalim.",
        notes = "Doa Nabi Yunus yang sangat agung untuk memohon jalan keluar dari kesempitan.",
        keywords = listOf("ujian", "uts", "uas", "deadline", "kesulitan", "stres", "yunus"),
      ),
      Prayer(
        id = "petunjuk_kebenaran",
        title = "Doa Memohon Petunjuk, Takwa & Kecukupan",
        category = PrayerCategory.KULIAH_ILMU,
        source = "HR. Muslim no. 2725",
        contextHint = "Dibaca agar selalu terbimbing dalam belajar, bekerja, dan menjaga diri",
        arabic = "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْهُدَى وَالتُّقَى وَالْعَفَافَ وَالْغِنَى",
        latin = "Allaahumma innii as-alukal hudaa wat-tuqaa wal ‘afaafa wal ghinaa.",
        translation =
          "Ya Allah, aku memohon kepada-Mu petunjuk, ketakwaan, keterjagaan diri dari hal haram, dan kekayaan hati.",
        notes = "Menggabungkan kebutuhan rohani dan kemandirian hidup mahasiswa.",
        keywords = listOf("petunjuk", "takwa", "cukup", "mandiri", "bimbingan"),
      ),
      Prayer(
        id = "tawakal_ikhtiar",
        title = "Doa Bertawakal Setelah Berusaha",
        category = PrayerCategory.KULIAH_ILMU,
        source = "QS. At-Taubah: 129",
        contextHint = "Dibaca setelah mengirim tugas, menyelesaikan ujian, atau menanti hasil",
        arabic = "حَسْبِيَ اللَّهُ لَا إِلَهَ إِلَّا هُوَ عَلَيْهِ تَوَكَّلْتُ وَهُوَ رَبُّ الْعَرْشِ الْعَظِيمِ",
        latin =
          "Hasbiyallaahu laa ilaaha illaa huwa ‘alaihi tawakkaltu wa huwa rabbul ‘arsyil ‘azhiim.",
        translation =
          "Cukuplah Allah bagiku, tidak ada Tuhan selain Dia. Hanya kepada-Nya aku bertawakal dan Dia adalah Tuhan pemilik ‘Arsy yang agung.",
        notes = "Menyerahkan hasil terbaik kepada Allah setelah ikhtiar maksimal.",
        keywords = listOf("tawakal", "pasrah", "hasil", "nilai", "ikhtiar", "hasbiyallah"),
      ),

      // ==========================================
      // KATEGORI 4: IBADAH & KETENANGAN (11 DOA)
      // ==========================================
      Prayer(
        id = "keteguhan_hati",
        title = "Doa Memohon Keteguhan Hati",
        category = PrayerCategory.IBADAH_KETENANGAN,
        source = "HR. Tirmidzi no. 2140",
        contextHint = "Dibaca di waktu tenang atau sepertiga malam agar hati istiqamah",
        arabic = "يَا مُقَلِّبَ الْقُلُوبِ ثَبِّتْ قَلْبِي عَلَى دِينِكَ",
        latin = "Yaa muqallibal quluub tsabbit qalbii ‘alaa diinik.",
        translation =
          "Wahai Dzat yang membolak-balikkan hati, teguhkanlah hatiku di atas agama-Mu.",
        notes = "Doa yang paling sering dibaca oleh Nabi Muhammad agar iman tetap kokoh.",
        keywords = listOf("hati", "istiqamah", "iman", "tahajud", "sepertiga malam", "teguh"),
      ),
      Prayer(
        id = "pertolongan_ibadah",
        title = "Doa Memohon Pertolongan dalam Ibadah & Syukur",
        category = PrayerCategory.IBADAH_KETENANGAN,
        source = "HR. Abu Dawud no. 1522",
        contextHint = "Dibaca setelah shalat fardhu atau di sela aktivitas siang",
        arabic = "اللَّهُمَّ أَعِنِّي عَلَى ذِكْرِكَ وَشُكْرِكَ وَحُسْنِ عِبَادَتِكَ",
        latin = "Allaahumma a‘innii ‘alaa dzikrika wa syukrika wa husni ‘ibaadatik.",
        translation =
          "Ya Allah, tolonglah aku agar senantiasa mengingat-Mu, bersyukur kepada-Mu, dan beribadah dengan baik kepada-Mu.",
        notes = "Wasiat langsung Rasulullah kepada sahabat Mu‘adz bin Jabal.",
        keywords = listOf("ibadah", "dzikir", "syukur", "dzuhur", "shalat", "khusyuk"),
      ),
      Prayer(
        id = "sayyidul_istighfar",
        title = "Sayyidul Istighfar (Penghulu Istighfar)",
        category = PrayerCategory.IBADAH_KETENANGAN,
        source = "HR. Bukhari no. 6306",
        contextHint = "Dibaca pada waktu pagi dan petang sebagai istighfar terbaik",
        arabic =
          "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ وَأَبُوءُ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ",
        latin =
          "Allaahumma anta rabbii laa ilaaha illaa anta khalaqtanii wa anaa ‘abduka wa anaa ‘alaa ‘ahdika wa wa‘dika mastatha‘tu, a‘uudzu bika min syarri maa shana‘tu, abuu-u laka bini‘matika ‘alayya wa abuu-u bidzanbii faghfir lii fa innahu laa yaghfirudz-dzunuuba illaa anta.",
        translation =
          "Ya Allah, Engkau adalah Tuhanku, tidak ada Tuhan selain Engkau. Engkau menciptakanku dan aku adalah hamba-Mu. Aku berada di atas janji dan ikrar-Mu semampuku. Aku berlindung kepada-Mu dari keburukan perbuatanku, aku mengakui nikmat-Mu atasku dan aku mengakui dosaku, maka ampunilah aku karena tidak ada yang mengampuni dosa selain Engkau.",
        notes = "Sangat dianjurkan menjadi wirid rutin sore/petang dan pagi hari.",
        keywords = listOf("istighfar", "ampun", "petang", "sore", "maghrib", "dzikir", "taubat"),
      ),
      Prayer(
        id = "kebaikan_dunia_akhirat",
        title = "Doa Kebaikan Dunia & Akhirat",
        category = PrayerCategory.IBADAH_KETENANGAN,
        source = "QS. Al-Baqarah: 201",
        contextHint = "Dibaca kapan saja, terutama di waktu malam untuk memohon kebaikan paripurna",
        arabic = "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ",
        latin =
          "Rabbanaa aatinaa fid-dunyaa hasanatan wa fil aakhirati hasanatan wa qinaa ‘adzaaban naar.",
        translation =
          "Ya Tuhan kami, berilah kami kebaikan di dunia dan kebaikan di akhirat, serta peliharalah kami dari siksa neraka.",
        notes = "Doa sapu jagat yang mencakup kebahagiaan hidup, ilmu, kesehatan, dan keselamatan akhirat.",
        keywords = listOf("sapu jagat", "dunia", "akhirat", "kebaikan", "malam", "tenang"),
      ),
      Prayer(
        id = "tahiyat_awal",
        title = "Bacaan Tahiyat Awal",
        category = PrayerCategory.IBADAH_KETENANGAN,
        source = "HR. Bukhari & Muslim",
        contextHint = "Dibaca saat duduk tasyahud awal pada rakaat kedua shalat",
        arabic =
          "التَّحِيَّاتُ الْمُبَارَكَاتُ الصَّلَوَاتُ الطَّيِّبَاتُ لِلَّهِ، السَّلَامُ عَلَيْكَ أَيُّهَا النَّبِيُّ وَرَحْمَةُ اللَّهِ وَبَرَكَاتُهُ، السَّلَامُ عَلَيْنَا وَعَلَى عِبَادِ اللَّهِ الصَّالِحِينَ، أَشْهَدُ أَنْ لَا إِلَهَ إِلَّا اللَّهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا رَسُولُ اللَّهِ، اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ",
        latin =
          "At-tahiyyaatul mubaarakaatush-shalawaatuth-thayyibaatu lillaah. As-salaamu ‘alaika ayyuhan-nabiyyu wa rahmatullaahi wa barakaatuh. As-salaamu ‘alainaa wa ‘alaa ‘ibaadillaahish-shaalihiin. Asyhadu allaa ilaaha illallaah, wa asyhadu anna muhammadar-rasuulullaah. Allaahumma shalli ‘alaa muhammad.",
        translation =
          "Segala kehormatan, keberkahan, shalawat, dan kebaikan adalah milik Allah. Semoga salam sejahtera tercurah kepadamu wahai Nabi, beserta rahmat dan berkah Allah. Semoga salam sejahtera tercurah kepada kami dan hamba-hamba Allah yang saleh. Aku bersaksi tiada Tuhan selain Allah, dan aku bersaksi Muhammad adalah utusan Allah. Ya Allah, limpahkanlah shalawat atas Nabi Muhammad.",
        notes = "Dilanjutkan dengan berdiri untuk rakaat ketiga pada shalat 3 atau 4 rakaat.",
        quickTips =
          listOf(
            "Kelompokkan menjadi 4 bagian pendek: Pembuka (At-tahiyyaat...), Salam (As-salaamu...), Syahadat (Asyhadu...), dan Shalawat pendek.",
            "Tarik napas tenang saat duduk iftirasy sebelum mulai membaca agar artikulasi jelas dan tidak terburu-buru.",
            "Fokus pada jeda koma di tiap frasa salam agar irama bacaan stabil.",
          ),
        keywords = listOf("tahiyat", "tasyahud", "shalat", "rakaat", "awal"),
      ),
      Prayer(
        id = "tahiyat_akhir",
        title = "Bacaan Tahiyat Akhir & Shalawat Ibrahimiyah",
        category = PrayerCategory.IBADAH_KETENANGAN,
        source = "HR. Bukhari & Muslim",
        contextHint = "Dibaca saat duduk tasyahud akhir sebelum salam",
        arabic =
          "التَّحِيَّاتُ الْمُبَارَكَاتُ الصَّلَوَاتُ الطَّيِّبَاتُ لِلَّهِ، السَّلَامُ عَلَيْكَ أَيُّهَا النَّبِيُّ وَرَحْمَةُ اللَّهِ وَبَرَكَاتُهُ، السَّلَامُ عَلَيْنَا وَعَلَى عِبَادِ اللَّهِ الصَّالِحِينَ، أَشْهَدُ أَنْ لَا إِلَهَ إِلَّا اللَّهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا رَسُولُ اللَّهِ، اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ، كَمَا صَلَّيْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ، وَبَارِكْ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ، كَمَا بَارَكْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ، فِي الْعَالَمِينَ إِنَّكَ حَمِيدٌ مَجِيدٌ",
        latin =
          "At-tahiyyaatul mubaarakaatush-shalawaatuth-thayyibaatu lillaah. As-salaamu ‘alaika ayyuhan-nabiyyu wa rahmatullaahi wa barakaatuh. As-salaamu ‘alainaa wa ‘alaa ‘ibaadillaahish-shaalihiin. Asyhadu allaa ilaaha illallaah, wa asyhadu anna muhammadar-rasuulullaah. Allaahumma shalli ‘alaa muhammad wa ‘alaa aali muhammad, kamaa shallaita ‘alaa ibraahiim wa ‘alaa aali ibraahiim, wa baarik ‘alaa muhammad wa ‘alaa aali muhammad, kamaa baarakta ‘alaa ibraahiim wa ‘alaa aali ibraahiim, fil ‘aalamiina innaka hamiidum majiid.",
        translation =
          "Segala kehormatan, keberkahan, shalawat, dan kebaikan adalah milik Allah. Semoga keselamatan tercurah kepadamu wahai Nabi beserta rahmat dan berkah Allah, serta kepada kami dan hamba-hamba Allah yang saleh. Aku bersaksi tiada Tuhan selain Allah dan Muhammad utusan Allah. Ya Allah, limpahkanlah shalawat kepada Muhammad dan keluarga Muhammad sebagaimana Engkau limpahkan kepada Ibrahim dan keluarga Ibrahim. Dan berkahilah Muhammad beserta keluarga Muhammad sebagaimana Engkau berkahi Ibrahim beserta keluarga Ibrahim. Di seluruh alam, sesungguhnya Engkau Maha Terpuji lagi Maha Mulia.",
        notes = "Setelah tasyahud akhir, disunnahkan membaca doa perlindungan sebelum mengucap salam.",
        quickTips =
          listOf(
            "Ingat pola berpasangan pada Shalawat Ibrahimiyah: 'Shalli ... kamaa shallaita' lalu 'Baarik ... kamaa baarakta'.",
            "Urutan nama selalu tetap: Muhammad & keluarga Muhammad terlebih dahulu, baru Ibrahim & keluarga Ibrahim.",
            "Baca dengan tempo sedang saat duduk tawarruk agar hati lebih khusyuk menjelang salam.",
          ),
        keywords = listOf("tahiyat akhir", "tasyahud akhir", "shalawat", "ibrahimiyah", "shalat"),
      ),
      Prayer(
        id = "sesudah_wudhu",
        title = "Doa Sesudah Wudhu",
        category = PrayerCategory.IBADAH_KETENANGAN,
        source = "HR. Muslim no. 234 & Tirmidzi",
        contextHint = "Dibaca setelah selesai berwudhu sambil menghadap kiblat",
        arabic =
          "أَشْهَدُ أَنْ لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ، اللَّهُمَّ اجْعَلْنِي مِنَ التَّوَّابِينَ وَاجْعَلْنِي مِنَ الْمُتَطَهِّرِينَ",
        latin =
          "Asyhadu allaa ilaaha illallaahu wahdahu laa syariika lah, wa asyhadu anna muhammadan ‘abduhu wa rasuuluh. Allaahummaj‘alnii minat-tawwaabiina waj‘alnii minal mutathahhiriin.",
        translation =
          "Aku bersaksi tiada Tuhan selain Allah Yang Maha Esa, tiada sekutu bagi-Nya, dan aku bersaksi bahwa Muhammad adalah hamba dan utusan-Nya. Ya Allah, jadikanlah aku termasuk orang-orang yang bertaubat dan jadikanlah aku termasuk orang-orang yang menyucikan diri.",
        notes = "Keutamaannya dibukakan delapan pintu surga bagi yang mengamalkannya dengan yakin.",
        keywords = listOf("wudhu", "bersuci", "shalat", "mushola", "air"),
      ),
      Prayer(
        id = "masuk_masjid",
        title = "Doa Masuk Masjid / Mushola",
        category = PrayerCategory.IBADAH_KETENANGAN,
        source = "HR. Muslim no. 713",
        contextHint = "Dibaca sambil melangkahkan kaki kanan masuk ke masjid atau mushola kampus",
        arabic = "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ",
        latin = "Allaahummaftah lii abwaaba rahmatik.",
        translation = "Ya Allah, bukakanlah untukku pintu-pintu rahmat-Mu.",
        notes = "Awali dengan salam dan shalawat kepada Nabi Muhammad sebelum membaca doa ini.",
        keywords = listOf("masjid", "mushola", "masuk", "shalat berjamaah", "jumat"),
      ),
      Prayer(
        id = "keluar_masjid",
        title = "Doa Keluar Masjid / Mushola",
        category = PrayerCategory.IBADAH_KETENANGAN,
        source = "HR. Muslim no. 713",
        contextHint = "Dibaca sambil melangkahkan kaki kiri keluar dari masjid atau mushola",
        arabic = "اللَّهُمَّ إِنِّي أَسْأَلُكَ مِنْ فَضْلِكَ",
        latin = "Allaahumma innii as-aluka min fadhlik.",
        translation = "Ya Allah, sesungguhnya aku memohon kepada-Mu sebagian dari karunia-Mu.",
        notes = "Memohon karunia rezeki dan keberkahan saat kembali beraktivitas di luar masjid.",
        keywords = listOf("masjid", "mushola", "keluar", "karunia", "aktivitas"),
      ),
      Prayer(
        id = "penenang_cemas",
        title = "Doa Penenang Hati Saat Gelisah & Sedih",
        category = PrayerCategory.IBADAH_KETENANGAN,
        source = "HR. Bukhari no. 6369",
        contextHint = "Dibaca ketika pikiran terasa penat, cemas, atau dilanda kesedihan",
        arabic =
          "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْهَمِّ وَالْحَزَنِ، وَالْعَجْزِ وَالْكَسَلِ، وَالْبُخْلِ وَالْجُبْنِ، وَضَلَعِ الدَّيْنِ وَغَلَبَةِ الرِّجَالِ",
        latin =
          "Allaahumma innii a‘uudzu bika minal hammi wal hazan, wal ‘ajzi wal kasal, wal bukhli wal jubn, wa dhala‘id-daini wa ghalabatir-rijaal.",
        translation =
          "Ya Allah, aku berlindung kepada-Mu dari rasa gelisah dan sedih, dari kelemahan dan kemalasan, dari sifat kikir dan pengecut, serta dari lilitan utang dan penindasan orang lain.",
        notes = "Membantu menata kembali semangat ketika merasa lelah atau kehilangan motivasi.",
        keywords = listOf("cemas", "sedih", "galau", "malas", "overthinking", "tenang", "stres"),
      ),
      Prayer(
        id = "keselamatan_pagi_petang",
        title = "Doa Pagi & Petang Memohon Keselamatan",
        category = PrayerCategory.IBADAH_KETENANGAN,
        source = "HR. Abu Dawud no. 5074 & Ibnu Majah",
        contextHint = "Dibaca rutin di pagi dan sore hari memohon kesehatan dan penjagaan diri",
        arabic =
          "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي الدُّنْيَا وَالْآخِرَةِ",
        latin = "Allaahumma innii as-alukal ‘afwa wal ‘aafiyata fid-dunyaa wal aakhirah.",
        translation =
          "Ya Allah, sesungguhnya aku memohon kepada-Mu ampunan dan keselamatan di dunia serta di akhirat.",
        notes = "Ringkas untuk menjaga kesehatan lahir batin, keluarga, dan aktivitas harian.",
        keywords = listOf("selamat", "pagi", "petang", "sehat", "afiat", "dzikir"),
      ),
    )

  val memorizationTips: List<MemorizationTip> =
    listOf(
      MemorizationTip(
        number = 1,
        title = "Potong jadi 2–3 frasa pendek",
        description =
          "Hafalkan sepotong pendek hingga lancar di lisan terlebih dahulu, lalu sambungkan ke potongan berikutnya sebelum pindah ke bagian akhir.",
      ),
      MemorizationTip(
        number = 2,
        title = "Baca langsung di momen aslinya",
        description =
          "Hafalan jauh lebih cepat menempel saat diucapkan langsung saat beraktivitas, seperti saat keluar pintu kos, naik kendaraan, atau sebelum mulai kuliah.",
      ),
      MemorizationTip(
        number = 3,
        title = "Uji hafalan dengan tombol Teks Latin",
        description =
          "Setelah beberapa kali membaca, matikan tampilan Teks Latin di halaman baca untuk melatih ingatan langsung dari lafal Arab atau hafalan.",
      ),
      MemorizationTip(
        number = 4,
        title = "Pahami alur arti kalimatnya",
        description =
          "Mengerti arti inti tiap kalimat membantu otak menyambungkan urutan bacaan secara logis agar tidak mudah tertukar saat lelah atau terburu-buru.",
      ),
      MemorizationTip(
        number = 5,
        title = "Ulang 3 kali sebelum tidur",
        description =
          "Pilih satu doa target dari daftar Tersimpan setiap hari, lalu ulangi tiga kali di waktu tenang sebelum tidur agar masuk ke memori jangka panjang.",
      ),
    )

  private fun findById(id: String): Prayer =
    prayers.firstOrNull { it.id == id } ?: prayers.first()

  fun getContextualRecommendation(
    calendar: Calendar = Calendar.getInstance(),
    timeZoneLabel: String = "WIB",
  ): ContextualRecommendation {
    return getContextualRecommendation(
      hour = calendar.get(Calendar.HOUR_OF_DAY),
      minute = calendar.get(Calendar.MINUTE),
      timeZoneLabel = timeZoneLabel,
    )
  }

  fun getContextualRecommendation(
    hour: Int,
    minute: Int,
    timeZoneLabel: String = "WIB",
  ): ContextualRecommendation {
    val safeHour = hour.coerceIn(0, 23)
    val safeMinute = minute.coerceIn(0, 59)
    val totalMinutes = safeHour * 60 + safeMinute
    val timeFormatted = String.format(Locale.US, "%02d:%02d", safeHour, safeMinute)

    val (contextLabel, prayerId) =
      when (totalMinutes) {
        in 180..269 -> "Sepertiga malam" to "keteguhan_hati" // 03:00 - 04:29
        in 270..359 -> "Waktu Subuh" to "bangun_tidur" // 04:30 - 05:59
        in 360..419 -> "Pagi hari" to "ilmu_bermanfaat" // 06:00 - 06:59
        in 420..509 -> "Berangkat pagi" to "keluar_rumah" // 07:00 - 08:29
        in 510..599 -> "Di perjalanan" to "naik_kendaraan" // 08:30 - 09:59
        in 600..704 -> "Waktu belajar" to "sebelum_belajar" // 10:00 - 11:44
        in 705..794 -> "Jam makan siang" to "sebelum_makan" // 11:45 - 13:14
        in 795..899 -> "Ibadah siang" to "pertolongan_ibadah" // 13:15 - 14:59
        in 900..989 -> "Kuliah & diskusi" to "kelancaran_lisan" // 15:00 - 16:29
        in 990..1064 -> "Aktivitas sore" to "dimudahkan_urusan" // 16:30 - 17:44
        in 1065..1154 -> "Petang & dzikir" to "sayyidul_istighfar" // 17:45 - 19:14
        in 1155..1259 -> "Evaluasi belajar" to "setelah_belajar" // 19:15 - 20:59
        in 1260..1349 -> "Ketenangan malam" to "kebaikan_dunia_akhirat" // 21:00 - 22:29
        else -> "Istirahat malam" to "sebelum_tidur" // 22:30 - 02:59
      }

    return ContextualRecommendation(
      timeFormatted = timeFormatted,
      contextLabel = contextLabel,
      timeZoneLabel = timeZoneLabel,
      prayer = findById(prayerId),
    )
  }

  fun filterPrayers(
    sourceList: List<Prayer>,
    query: String,
    category: PrayerCategory,
  ): List<Prayer> {
    val trimmed = query.trim().lowercase(Locale("id", "ID"))
    return sourceList.filter { prayer ->
      val matchesCategory = category == PrayerCategory.ALL || prayer.category == category
      if (!matchesCategory) return@filter false
      if (trimmed.isEmpty()) return@filter true

      prayer.title.lowercase(Locale("id", "ID")).contains(trimmed) ||
        prayer.category.label.lowercase(Locale("id", "ID")).contains(trimmed) ||
        prayer.contextHint.lowercase(Locale("id", "ID")).contains(trimmed) ||
        prayer.latin.lowercase(Locale("id", "ID")).contains(trimmed) ||
        prayer.translation.lowercase(Locale("id", "ID")).contains(trimmed) ||
        prayer.source.lowercase(Locale("id", "ID")).contains(trimmed) ||
        prayer.notes.lowercase(Locale("id", "ID")).contains(trimmed) ||
        prayer.quickTips.any { it.lowercase(Locale("id", "ID")).contains(trimmed) } ||
        prayer.keywords.any { it.lowercase(Locale("id", "ID")).contains(trimmed) }
    }
  }
}
