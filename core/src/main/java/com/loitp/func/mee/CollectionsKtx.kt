/**
 ** Author : Abdelmajid ID ALI
 ** On : 17/08/2021
 ** Email :  abdelmajid.idali@gmail.com
 **/


fun List<String>.removeComments(): List<String> {
    return this.filter {
        !it.startsWith("#") &&
                !it.startsWith("//") &&
                !it.startsWith("/**")
    }
}
