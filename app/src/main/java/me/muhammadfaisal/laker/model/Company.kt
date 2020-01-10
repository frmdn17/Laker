package me.muhammadfaisal.laker.model

class Company(
    var companyName : String,
    var companyAddress : String,
    var companyEmail : String,
    var companyPhoneNumber : String,
    var companyPassword : String,
    var companyStatus : Int = 0
){
    constructor() :  this("","","","","",0)
}