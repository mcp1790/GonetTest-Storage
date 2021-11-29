package mx.test.android.gonet.storagelib.entity

import io.realm.RealmList
import io.realm.RealmObject
import mx.test.android.gonet.storagelib.entity.child.GenreRealmEntity

open class TvShowRawRealmEntity: RealmObject()  {
    var backdrop_path: String = ""
    var first_air_date: String = ""
    var genre_ids: RealmList<Int> = RealmList()
    var genres: RealmList<GenreRealmEntity> = RealmList()
    var id: Int = -1
    var name: String = ""
    var origin_country: RealmList<String> = RealmList()
    var original_language: String = ""
    var original_name: String = ""
    var overview: String = ""
    var popularity: Double = -1.0
    var poster_path: String = ""
    var vote_average: Double = -1.0
    var vote_count: Int = -1
}