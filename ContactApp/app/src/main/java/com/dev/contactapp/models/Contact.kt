package com.dev.contactapp.models
class Contact {
    var id:Int
    var name:String
    var phone:String

    constructor(id: Int, name: String, phone: String) {
        this.id = id
        this.name = name
        this.phone = phone
    }
}