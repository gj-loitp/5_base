package vn.loitp.app.a.cv.webview.wrapContent

/**
 * Created by Loitp on 26,September,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object HtmlContent {

    //phai bo het cac height thi moi work
    const val body = "\n" +
            "  <html>\n" +
            "      <head>\n" +
            "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
            "        <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\"/>\n" +
            "        <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin/>\n" +
            "        <link href=\"https://fonts.googleapis.com/css2?family=Bungee&family=Cormorant+Infant&f/amily=Dancing+Script&family=Inter&family=Lobster&family=Lora&family=Noto+Sans&family=Playfair+Display&family=Inter&family=Whisper&display=swap\" rel=\"stylesheet\"/>\n" +
            "      \n" +
            "        <style>\n" +
            "          *{\n" +
            "            box-sizing:border-box;\n" +
            "          }\n" +
            "          html {\n" +
            "            height: 100%;\n" +
            "            widht: 100%;\n" +
            "          }\n" +
            "          body {\n" +
            "            font-family: -apple-system, BlinkMacSystemFont, 'Inter', sans-serif;\n" +
            "            width: 100%;\n" +
            "            height: 100%;\n" +
            "            display: flex;\n" +
            "            align-items: center;\n" +
            "            justify-content: center;\n" +
            "            overflow: hidden;\n" +
            "            margin:0;\n" +
            "          }\n" +
            "          .container {\n" +
            "            overflow: hidden;\n" +
            "            display: flex;\n" +
            "            justify-content: center;\n" +
            "            align-items: center;\n" +
            "            width:90%;\n" +
            "            height:100%;\n" +
            "          }\n" +
            "           .pmp-message-container{\n" +
            "            max-height:90%;\n" +
            "            position:relative;\n" +
            "            border-radius:8px;\n" +
            "            overflow:auto;\n" +
            "          }\n" +
            "          .pmp-message-close-icon{\n" +
            "            color:#9ca3af;\n" +
            "            height:24px;\n" +
            "            width:24px;\n" +
            "          }\n" +
            "          .pmp-message-content-label {\n" +
            "            font-size: 14px;;\n" +
            "            line-height: 20px;;\n" +
            "          }\n" +
            "          .pmp-message-content-content {\n" +
            "            font-size: 24px;\n" +
            "            line-height: 32px;\n" +
            "          } \n" +
            "          .pmp-message-close-icon {\n" +
            "        color: #ffffff;\n" +
            "        height: 24px;\n" +
            "        width: 24px;\n" +
            "      }\n" +
            "      .pmp-message-container{\n" +
            "        display: flex;\n" +
            "        flex-direction: column;\n" +
            "        justify-content: center;\n" +
            "      }\n" +
            "      .pmp-message-image-img {\n" +
            "        width: 100%;\n" +
            "      }\n" +
            "      .pmp-message-image {\n" +
            "        border-radius: 8px;\n" +
            "        overflow: hidden;\n" +
            "      }\n" +
            "      .pmp-message-close-button {\n" +
            "        display: flex;\n" +
            "        justify-content: flex-end;\n" +
            "        margin-bottom: 16px;\n" +
            "      }\n" +
            "        </style>\n" +
            "      </head>\n" +
            "      <body>\n" +
            "        <div class=\"container\">\n" +
            "            <div id=\"pmp-message\" class=\"pmp-message-container\">\n" +
            "            <div onclick=\"onClickClose.performClick(this.value);\" id=\"pmp-browser-message-close\" class=\"pmp-message-close-button cursor-pointer\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 20 20\" fill=\"currentColor\" class=\"pmp-message-close-icon\"><path fill-rule=\"evenodd\" d=\"M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z\" clip-rule=\"evenodd\"></path></svg>\n" +
            "                </div>\n" +
            "                <a  onclick=\"onClickBody.performClick(this.value);\" href=\"javascript:void(0)\" class=\"pmp-message-image\"><img class=\"pmp-message-image-img\" src=\"https://dy4ter0nj042r.cloudfront.net/apps/pmp/2022-07-19/1658223931825.Rectangle%201127%20%282%29.png\" alt=\"\"></a>\n" +
            "                </div>\n" +
            "                </div>\n" +
            "      </body>\n" +
            "    </html>\n"
}