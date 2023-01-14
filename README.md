# DocDelivery(DocDelivery will be live soon)
[![CodeFactor](https://www.codefactor.io/repository/github/shankarpriyank/docdelivery/badge)](https://www.codefactor.io/repository/github/shankarpriyank/docdelivery)
[![Build](https://github.com/shankarpriyank/Dr.Delivery/actions/workflows/build.yml/badge.svg)](https://github.com/shankarpriyank/Dr.Delivery/actions/workflows/build.yml)  

DocDelivery is a package/shipment tracking application that helps the user track their incoming packages/shipments easily.

DocDelivery is built using Kotlin and it leverages Jetpack Compose, Dagger-Hilt, Coroutines etc. It uses Gmail API to fetch the users email.
For detailed information on how DocDelivery is developed see [development wiki]()

## Login UI
<p align="center"> 
  <img src="https://user-images.githubusercontent.com/100941430/205707074-e4396320-8470-41ff-aaa2-b26ed1f5c1d3.gif" alt="animated " height="10%" width="30%"  />
</p> 

<!--  <p align="center"> 
  <img src="https://user-images.githubusercontent.com/100941430/205707074-e4396320-8470-41ff-aaa2-b26ed1f5c1d3.gif" alt="animated " height=100>
</p> -->



## Installation

Linux,Mac or Windows should work to build DocDelivery.\
Install [Android Studio](https://developer.android.com/studio)\
If you plan to contribute and propose pull requests then use a web interface at [https://github.com/shankarpriyank/DocDelivery](https://github.com/shankarpriyank/DocDelivery) to fork the repo first and use your fork's url in the command below.

```bash
git clone https://github.com/shankarpriyank/DocDelivery
```
To run the app you will also need to setup a new project in gcp console.
1. Go to your [gcp console](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwjV7sz2xsv7AhX9TGwGHSrECTAQFnoECA0QAQ&url=https%3A%2F%2Fconsole.cloud.google.com%2F&usg=AOvVaw1GxwHR1WZnDu0xsR-djCrv)
2. Setup your OAuth consent screen, this article might [help](https://medium.com/@hamza.azee91/google-sign-in-into-your-android-app-step-by-step-57550e6e9398).
3. Enable Gmail api in your project.
4. Add your personal email, or whatever suits you as testing users so that you you can run your application with those emails.
4. Replace ``` ${{ secrets.API_KEY }}```  in this [file](https://github.com/shankarpriyank/DocDelivery/blob/master/app/src/main/res/values/strings.xml#L6) with your client id, which you got from gcp console.

After all this is done run the command below in the terminal of your Android Studio to check if everything is working fine.
```bash
./gradlew assembleDebug
```





## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to follow the development style, and abide by our code formatting guidelines.

## License

[MIT](https://choosealicense.com/licenses/mit/)
