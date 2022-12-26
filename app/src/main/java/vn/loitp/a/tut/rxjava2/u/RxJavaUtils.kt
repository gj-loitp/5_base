package vn.loitp.a.tut.rxjava2.u

import vn.loitp.a.tut.rxjava2.md.ApiUser
import vn.loitp.a.tut.rxjava2.md.User

class RxJavaUtils {
    companion object {
        @Suppress("unused")
        val userList: List<User>
            get() {
                val userList: MutableList<User> = ArrayList()

                val userOne = User()
                userOne.firstname = "Amit"
                userOne.lastname = "Shekhar"
                userList.add(userOne)

                val userTwo = User()
                userTwo.firstname = "Manish"
                userTwo.lastname = "Kumar"
                userList.add(userTwo)

                val userThree = User()
                userThree.firstname = "Sumit"
                userThree.lastname = "Kumar"
                userList.add(userThree)
                return userList
            }

        @JvmStatic
        val apiUserList: List<ApiUser>
            get() {
                val apiUserList: MutableList<ApiUser> = ArrayList()

                val apiUserOne = ApiUser()
                apiUserOne.firstname = "Amit"
                apiUserOne.lastname = "Shekhar"
                apiUserList.add(apiUserOne)

                val apiUserTwo = ApiUser()
                apiUserTwo.firstname = "Manish"
                apiUserTwo.lastname = "Kumar"
                apiUserList.add(apiUserTwo)

                val apiUserThree = ApiUser()
                apiUserThree.firstname = "Sumit"
                apiUserThree.lastname = "Kumar"
                apiUserList.add(apiUserThree)

                return apiUserList
            }

        @JvmStatic
        fun convertApiUserListToUserList(apiUserList: List<ApiUser>): List<User> {
            val userList: MutableList<User> = ArrayList()
            for (apiUser in apiUserList) {
                val user = User()
                user.firstname = apiUser.firstname
                user.lastname = apiUser.lastname
                userList.add(user)
            }
            return userList
        }

        @Suppress("unused")
        fun convertApiUserListToApiUserList(apiUserList: List<ApiUser>): List<ApiUser> {
            return apiUserList
        }

        @Suppress("unused")
        val userListWhoLovesCricket: List<User>
            get() {
                val userList: MutableList<User> = ArrayList()

                val userOne = User()
                userOne.id = 1
                userOne.firstname = "Amit"
                userOne.lastname = "Shekhar"
                userList.add(userOne)

                val userTwo = User()
                userTwo.id = 2
                userTwo.firstname = "Manish"
                userTwo.lastname = "Kumar"
                userList.add(userTwo)

                return userList
            }

        @Suppress("unused")
        val userListWhoLovesFootball: List<User>
            get() {
                val userList: MutableList<User> = ArrayList()

                val userOne = User()
                userOne.id = 1
                userOne.firstname = "Amit"
                userOne.lastname = "Shekhar"
                userList.add(userOne)

                val userTwo = User()
                userTwo.id = 3
                userTwo.firstname = "Sumit"
                userTwo.lastname = "Kumar"
                userList.add(userTwo)

                return userList
            }

        @Suppress("unused")
        fun filterUserWhoLovesBoth(
            cricketFans: List<User>,
            footballFans: List<User>
        ): List<User> {
            val userWhoLovesBoth: MutableList<User> = ArrayList()
            for (cricketFan in cricketFans) {
                for (footballFan in footballFans) {
                    if (cricketFan.id == footballFan.id) {
                        userWhoLovesBoth.add(cricketFan)
                    }
                }
            }
            return userWhoLovesBoth
        }
    }
}
