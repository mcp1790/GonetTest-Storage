package mx.test.android.gonet.storagelib.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import mx.test.android.gonet.storagelib.entity.child.ProductionCountryRealmEntity
import mx.test.android.gonet.storagelib.entity.child.GenreRealmEntity
import mx.test.android.gonet.storagelib.entity.child.ProductionCompanyRealmEntity
import mx.test.android.gonet.storagelib.entity.child.SpokenLanguageRealmEntity

open class MovieRawRealmEntity: RealmObject() {
    var adult: Boolean = false
    var backdrop_path: String = ""
    var budget: Int = -1
    var genre_ids: List<Int> = listOf()
    var genres: RealmList<GenreRealmEntity> = RealmList()
    var homepage: String = ""
    @PrimaryKey
    var id: Int = -1
    var imdb_id: String = ""
    var original_language: String = ""
    var original_title: String = ""
    var overview: String = ""
    var popularity: Double = -1.0
    var poster_path: String = ""
    var production_companies: RealmList<ProductionCompanyRealmEntity> = RealmList()
    var production_countries: RealmList<ProductionCountryRealmEntity> = RealmList()
    var release_date: String = ""
    var revenue: Int = -1
    var runtime: Int = -1
    var spoken_languages: RealmList<SpokenLanguageRealmEntity> = RealmList()
    var status: String = ""
    var tagline: String = ""
    var title: String = ""
    var video: Boolean = false
    var vote_average: Double = -1.0
    var vote_count: Int = -1
}