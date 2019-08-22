package com.starwars.controller

import com.github.jasync.sql.db.QueryResult
import com.github.jasync.sql.db.mysql.MySQLQueryResult
import com.starwars.dao.PlanetDAO
import com.starwars.models.Planet
import com.starwars.models.SWApiPlanetSearchResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.reactivex.Maybe
import io.reactivex.MaybeSource
import io.reactivex.Observable
import io.reactivex.ObservableSource
import javax.inject.Inject

@Controller("/planets")
class APIController {

    @Inject
    lateinit var dao: PlanetDAO

    @Inject
    @field:Client("https://swapi.co/api")
    lateinit var httpClient: RxHttpClient

    @Get
    fun list(): Observable<MutableHttpResponse<List<Planet>>> {
        return dao.findAll()
                .flatMap {
                    Observable.just(HttpResponse.ok(it))
                }
    }

    @Get("/{id}")
    fun getById(id: Long): Observable<MutableHttpResponse<Planet?>> {
        return dao.getById(id)
                .flatMap {
                    Observable.just(HttpResponse.ok(it))
                }
                .onErrorReturn {
                    HttpResponse.notFound()
                }
    }

    @Post
    fun create(planet: Planet): Observable<MutableHttpResponse<HttpStatus?>> {
        return httpClient.exchange("/planets?search=${planet.name}", SWApiPlanetSearchResponse::class.java)
                .firstElement()
                .flatMapObservable {
                    val appearanceFilms = if (it.status == HttpStatus.OK && it.body()!!.count > 0) {
                        it.body()!!.results[0].films.size
                    } else {
                        0
                    }

                    planet.appearanceFilms = appearanceFilms

                    dao.create(planet)
                        .flatMap { queryResult ->
                            if (queryResult.rowsAffected > 0) {
                                Observable.just(HttpResponse.status<HttpStatus>(HttpStatus.CREATED))
                            } else {
                                Observable.just(HttpResponse.status<HttpStatus>(HttpStatus.NOT_ACCEPTABLE))
                            }
                        }

                }
    }

    @Post("/search", consumes = [MediaType.APPLICATION_FORM_URLENCODED])
    fun searchByName(q: String): Observable<MutableHttpResponse<List<Planet>>> {
        return dao.findByName(q)
                .flatMap {
                    Observable.just(HttpResponse.ok(it))
                }
    }

    @Delete("/{id}")
    fun remove(id: Long): Observable<MutableHttpResponse<HttpStatus>> {
        return dao.delete(id)
                .flatMap {
                    if (it.rowsAffected > 1) {
                        Observable.just(HttpResponse.status<HttpStatus>(HttpStatus.OK))
                    } else {
                        Observable.just(HttpResponse.status<HttpStatus>(HttpStatus.CONFLICT))
                    }
                }
    }

}