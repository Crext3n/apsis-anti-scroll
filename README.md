# Apsis
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg) ![Version](https://img.shields.io/badge/version-1.6.0-blue.svg)

## EN
### Application Features
Apsis is an open-source mobile accessibility monitoring service designed to limit access to short-form vertical video content such as Instagram Reels, TikTok videos, and YouTube Shorts.  
After the user installs the application via APK or internal Google Play testing and enables accessibility permissions, the service becomes active and starts monitoring interface elements at regular intervals.

The application scans screen components to detect whether a short-video interface is currently displayed. If a prohibited component is identified, Apsis automatically triggers a “back” action and prevents the user from continuing the video feed.  
To avoid false triggers, it checks multiple indicators and ignores elements that are placed on a whitelist.

Once accessibility permissions are enabled, the interface displays a password-reset button and a status indicator showing whether the service is running. It also provides warning messages when resetting the password.  
Additionally, there is a compatibility switch for older Instagram versions, ensuring more stable component detection on legacy builds.

### How it works
Apsis identifies the launch of Instagram, TikTok, or YouTube by monitoring package names such as `com.instagram.android`. When one of these apps is active, it continuously scans the UI hierarchy for specific components (e.g., `com.instagram.android:id/comment_button`) that are characteristic of Reels/TikTok/Shorts interfaces.

Due to version differences and UI variability, the application checks multiple components to ensure more stable and accurate detection.

### Those who contributed
- Software: [crext3n.xyz](https://crext3n.xyz/)
- App icon: [@bariis.eroglu](https://www.instagram.com/bariis.eroglu)

## License
This project is licensed under the MIT License — see the LICENSE file for details.

---

## TR
### Uygulamanın Özellikleri
Apsis, Instagram Reels, TikTok videoları ve YouTube Shorts gibi dikey kısa video içeriklerine erişimi sınırlamak amacıyla geliştirilmiş açık kaynaklı bir mobil erişilebilirlik izleme servisidir.  
Kullanıcı uygulamayı APK veya Google Play Store dahili test programı üzerinden yükleyip erişilebilirlik iznini verdikten sonra servis aktif hâle gelir ve ekrandaki arayüz bileşenlerini belirli aralıklarla taramaya başlar.

Uygulama, kısa video arayüzlerine özgü bileşenleri algıladığında otomatik olarak “geri” eylemini tetikler ve kullanıcının video akışında kalmasını engeller.  
Hataları önlemek amacıyla birden fazla bileşeni kontrol eder ve beyaz listede bulunan öğeleri gördüğünde geri komutunu çalıştırmaz.

Erişilebilirlik ayarı açıldıktan sonra arayüzde şifre sıfırlama düğmesi belirir ve servisin aktif olup olmadığına dair durum bilgisi gösterilir. Şifre sıfırlama sırasında kullanıcıya uyarı mesajları sunulur.  
Buna ek olarak, eski Instagram sürümleriyle daha uyumlu çalışan bileşenleri taramak için özel bir “eski sürüm modu” anahtarı da bulunur.

### Nasıl çalışıyor
Uygulama, Instagram, TikTok veya YouTube’un açıldığını `com.instagram.android` gibi paket adlarını izleyerek tespit eder. Bu uygulamalardan biri çalıştığında, Reels/TikTok/Shorts arayüzüne özgü bileşenleri (ör. `com.instagram.android:id/comment_button`) bulmak için ekranı düzenli aralıklarla tarar.

Sürüm farklarından kaynaklanan değişikliklere uyum sağlayabilmek için birden fazla bileşen kontrol edilerek daha kararlı bir algılama sağlanır.

### Emeği Geçenler
- Yazılım: [crext3n.xyz](https://crext3n.xyz/)
- Uygulama Logosu: [@bariis.eroglu](https://www.instagram.com/bariis.eroglu)

## Lisans
Bu proje MIT Lisansı kapsamında lisanslanmıştır; ayrıntılar için LICENSE dosyasına bakabilirsiniz.
