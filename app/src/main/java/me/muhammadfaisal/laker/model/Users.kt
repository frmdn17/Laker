package me.muhammadfaisal.laker.model

class Users(
    var name: String,
    var email: String,
    var userID: String,
    var about: String,
    var experience : String,
    var phoneNumber: String,
    var statusAccount: Int = 0,
    var role : Int = 0
) {
    constructor() : this("", "","", "","","", 0, 0){}
}
