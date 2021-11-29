package mx.test.android.gonet.storagelib.entity.child

import io.realm.RealmObject

open class ProductionCompanyRealmEntity: RealmObject(){
    var id: Int = -1
    var logo_path: String = ""
    var name: String = ""
    var origin_country: String = ""
}