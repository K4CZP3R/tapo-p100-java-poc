# TP-Link Tapo P100 Smart Switch Control

First proof of Concept by K4CZP3R through reverse engineering. 

Writeup can be found at: https://k4czp3r.xyz/reverse-engineering/tp-link/tapo/2020/10/15/reverse-engineering-tp-link-tapo.html

## Usage

Compile and package with 

```
mvn package
```

then run 
```
java -jar target/tapop100-1.0-SNAPSHOT-jar-with-dependencies.jar true
```

to turn the switch on, and 

```
java -jar target/tapop100-1.0-SNAPSHOT-jar-with-dependencies.jar false
```

to turn the switch of. Configure your settings in the generated tapop100.properties file. 

---
[buymecoffee]: https://www.buymeacoffee.com/k4czp3r
[buymecoffeebadge]: https://www.buymeacoffee.com/assets/img/custom_images/yellow_img.png
