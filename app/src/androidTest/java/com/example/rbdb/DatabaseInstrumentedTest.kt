package com.example.rbdb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.dao.*
import com.example.rbdb.database.model.*
import com.example.rbdb.ui.arch.AppRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.Executors


@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest {


    //    private lateinit var userDao: UserDao
    private lateinit var cardEntityDao: CardEntityDao
    private lateinit var tagEntityDao: TagEntityDao
    private lateinit var cardListCrossRefDao: CardListCrossRefDao
    private lateinit var cardTagCrossRefDao: CardTagCrossRefDao
    private lateinit var listEntityDao: ListEntityDao
    private lateinit var appRepository:AppRepository



    //    private lateinit var db: TestDatabase
    private lateinit var db: AppDatabase
//
//    @Before
//    fun createDb() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        db = Room.inMemoryDatabaseBuilder(
//            context, TestDatabase::class.java).build()
//        userDao = db.getUserDao()
//    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
        cardEntityDao = db.cardEntityDao()
        tagEntityDao =db.tagEntityDao()
        cardListCrossRefDao = db.cardListCrossRefDao()
        listEntityDao = db.listEntityDao()
        cardTagCrossRefDao = db.cardTagCrossRefDao()
        appRepository= AppRepository(db)
    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

//
//    @Test
//    @Throws(Exception::class)
//    fun writeUserAndReadInList() {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
//        userDao.insert(user)
//        val byName = userDao.findUsersByName("george")
//        assertThat(byName.get(0), equalTo(user))
//    }

    @Test
    @Throws(Exception::class)
    fun addCardEntity() = runBlocking {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity: CardEntity = CardEntity(
            1, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )

        cardEntityDao.insert(cardEntity)

        val allCards = cardEntityDao.getAllCards()
        println(allCards)

        assertThat(allCards.isEmpty(), equalTo(false))
    }


    @Test
    @Throws(Exception::class)
    fun searchCardByName() = runBlocking {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity1: CardEntity = CardEntity(
            1, "poksamok", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity2: CardEntity = CardEntity(
            2, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a red guy"
        )
        val cardEntity3: CardEntity = CardEntity(
            3, "samer", "unimelb",
            "0922", "444222999", "test@email.com", "I am a blue guy"
        )
        val cardEntity4: CardEntity = CardEntity(
            4, "adam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a green guy"
        )
        val cardEntity5: CardEntity = CardEntity(
            5, "Zerg", "unimelb",
            "0922", "444222999", "test@email.com", "I am a yellow guy"
        )

        cardEntityDao.insert(cardEntity1)
        cardEntityDao.insert(cardEntity2)
        cardEntityDao.insert(cardEntity3)
        cardEntityDao.insert(cardEntity4)
        cardEntityDao.insert(cardEntity5)

        val cards = appRepository.getCardsByName("Sam")

        for(card in cards){
            println(card)
        }

        assertThat(cards.size, equalTo(3))
    }

    @Test
    @Throws(Exception::class)
    fun getAllCardsOrderByName() = runBlocking {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity1: CardEntity = CardEntity(
            1, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity2: CardEntity = CardEntity(
            0, "jack", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity3: CardEntity = CardEntity(
            0, "peter", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity4: CardEntity = CardEntity(
            0, "adam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity5: CardEntity = CardEntity(
            0, "Zerg", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )

        cardEntityDao.insert(cardEntity1)
        cardEntityDao.insert(cardEntity2)
        cardEntityDao.insert(cardEntity3)
        cardEntityDao.insert(cardEntity4)
        cardEntityDao.insert(cardEntity5)

        val cards = appRepository.getAllCardsOrderByName()
        println("0930 hello")
        for (card in cards) {
            println(card)
        }

        assertThat(cards[0].name, equalTo("adam"))
        assertThat(cards[1].name, equalTo("jack"))
        assertThat(cards[2].name, equalTo("peter"))
        assertThat(cards[3].name, equalTo("sam"))
        assertThat(cards[4].name, equalTo("Zerg"))


//        assertThat(cards[0], equalTo(cardEntity))
    }

    @Test
    @Throws(Exception::class)
    fun searchByKeywordIndescription() = runBlocking {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity1: CardEntity = CardEntity(
            1, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity2: CardEntity = CardEntity(
            0, "jack", "unimelb",
            "0922", "444222999", "test@email.com", "I am a red guy"
        )
        val cardEntity3: CardEntity = CardEntity(
            0, "peter", "unimelb",
            "0922", "444222999", "test@email.com", "I am a blue guy"
        )
        val cardEntity4: CardEntity = CardEntity(
            100, "adam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a green guy"
        )
        val cardEntity5: CardEntity = CardEntity(
            0, "Zerg", "unimelb",
            "0922", "444222999", "test@email.com", "I am a yellow guy"
        )

        cardEntityDao.insert(cardEntity1)
        cardEntityDao.insert(cardEntity2)
        cardEntityDao.insert(cardEntity3)
        cardEntityDao.insert(cardEntity4)
        cardEntityDao.insert(cardEntity5)

        val cards = appRepository.getCardsByKeywordInDescription("green")
        println("0930 hello")
        for (card in cards) {
            println(card)
        }

        assertThat(cards.size, equalTo(1))
        assertThat(cards[0], equalTo(cardEntity4))

//        assertThat(cards[0], equalTo(cardEntity))
    }

    @Test
    @Throws(Exception::class)
    fun insertCardListCrossReference() = runBlocking {

        val cardListCrossRef: CardListCrossRef = CardListCrossRef(100, 200)

        cardListCrossRefDao.insert(cardListCrossRef)
        val results: List<CardListCrossRef> = cardListCrossRefDao.getAllCardListCrossRef()

        println("0930 hello")
        for (result in results) {
            println(result)
        }

        assertThat(results[0], equalTo(cardListCrossRef))

    }

    @Test
    @Throws(Exception::class)
    fun getListsWithCards() = runBlocking {

        val cardListCrossRef: CardListCrossRef = CardListCrossRef(100, 200)
        val cardListCrossRef2: CardListCrossRef = CardListCrossRef(50, 200)
        val cardEntity: CardEntity = CardEntity(
            100, "Zerg", "unimelb",
            "0922", "444222999", "test@email.com", "I am a yellow guy"
        )
        val cardEntity2: CardEntity = CardEntity(
            50, "non-sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a yellow guy"
        )
        val listEntity: ListEntity = ListEntity(200, "first list")
        val listEntity2: ListEntity = ListEntity(300, "second list")


        listEntityDao.insert(listEntity)
        listEntityDao.insert(listEntity2)
        cardEntityDao.insert(cardEntity)
        cardEntityDao.insert(cardEntity2)
        cardListCrossRefDao.insert(cardListCrossRef)
        cardListCrossRefDao.insert(cardListCrossRef2)

        val results: List<ListWithCardsEntity> = listEntityDao.getListWithCards()

        println("0930 hello")
        for (result in results) {
            println(result)
        }

//        assertThat(results[0], equalTo(cardListCrossRef));

    }

    @Test
    @Throws(Exception::class)
    fun deleteCardById() = runBlocking {

        var cardEntity: CardEntity = CardEntity(
            10, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )

        val id = cardEntityDao.insert(cardEntity)

        cardEntityDao.deleteCardById(id)

        cardEntity = cardEntityDao.getCardById(id)

        assertThat(cardEntity, equalTo(null))

    }

    @Test
    @Throws(Exception::class)
    fun deleteCardWithCrossRefByCardId() = runBlocking {

        val cardEntity: CardEntity = CardEntity(
            10, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val id = cardEntityDao.insert(cardEntity)

        val cardListCrossRef:CardListCrossRef = CardListCrossRef(id, 100)
        cardListCrossRefDao.insert(cardListCrossRef)

        val cardTagCrossRef:CardTagCrossRef = CardTagCrossRef(id, 200)
        cardTagCrossRefDao.insert(cardTagCrossRef)

        appRepository.deleteCardAndCrossRefByCardId(id)

        assertThat(cardEntityDao.getCardById(id), equalTo(null))
        assertThat(cardListCrossRefDao.getAllCardListCrossRef().isEmpty(), equalTo(true))
        assertThat(cardTagCrossRefDao.getAllCardTagCrossRef().isEmpty(), equalTo(true))

    }

    @Test
    @Throws(Exception::class)
    fun getListWithCardsByListId() = runBlocking {

        val cardEntity1: CardEntity = CardEntity(
            1, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity2: CardEntity = CardEntity(
            2, "jack", "unimelb",
            "0922", "444222999", "test@email.com", "I am a red guy"
        )
        val cardEntity3: CardEntity = CardEntity(
            3, "peter", "unimelb",
            "0922", "444222999", "test@email.com", "I am a blue guy"
        )
        val cardEntity4: CardEntity = CardEntity(
            4, "adam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a green guy"
        )
        val cardEntity5: CardEntity = CardEntity(
            5, "Zerg", "unimelb",
            "0922", "444222999", "test@email.com", "I am a yellow guy"
        )

        cardEntityDao.insert(cardEntity1)
        cardEntityDao.insert(cardEntity2)
        cardEntityDao.insert(cardEntity3)
        cardEntityDao.insert(cardEntity4)
        cardEntityDao.insert(cardEntity5)

        val listEntity:ListEntity = ListEntity(100,"first list")
        listEntityDao.insert(listEntity)

        val cardListCrossRef1:CardListCrossRef = CardListCrossRef(1,100)
        val cardListCrossRef2:CardListCrossRef = CardListCrossRef(2,100)
        val cardListCrossRef3:CardListCrossRef = CardListCrossRef(3,100)

        cardListCrossRefDao.insert(cardListCrossRef1)
        cardListCrossRefDao.insert(cardListCrossRef2)
        cardListCrossRefDao.insert(cardListCrossRef3)

        val listWithCardsEntity:ListWithCardsEntity = listEntityDao.getListWithCardsByListId(100)

        assertThat(listWithCardsEntity.listEntity.listId, equalTo(100))
        assertThat(listWithCardsEntity.cards[0].cardId, equalTo(1))
        assertThat(listWithCardsEntity.cards[1].cardId, equalTo(2))
        assertThat(listWithCardsEntity.cards[2].cardId, equalTo(3))

    }

    @Test
    @Throws(Exception::class)
    fun getCardByTagIds() = runBlocking {

        /*val result: List<CardEntity> = appRepository.getCardByTagIds()
        println(result)*/

        val tagEntity1:TagEntity = TagEntity(100,"first tag")
        val tagEntity2:TagEntity = TagEntity(200,"second tag")
        tagEntityDao.insert(tagEntity1)
        tagEntityDao.insert(tagEntity2)

        val cardEntity1: CardEntity = CardEntity(
            1, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity2: CardEntity = CardEntity(
            2, "jack", "unimelb",
            "0922", "444222999", "test@email.com", "I am a red guy"
        )
        val cardEntity3: CardEntity = CardEntity(
            3, "peter", "unimelb",
            "0922", "444222999", "test@email.com", "I am a blue guy"
        )
        cardEntityDao.insert(cardEntity1)
        cardEntityDao.insert(cardEntity2)
        cardEntityDao.insert(cardEntity3)

        val cardTagCrossRef1:CardTagCrossRef = CardTagCrossRef(1,100)
        val cardTagCrossRef2:CardTagCrossRef = CardTagCrossRef(2,100)
        val cardTagCrossRef3:CardTagCrossRef = CardTagCrossRef(2,200)
//        val cardTagCrossRef4:CardTagCrossRef = CardTagCrossRef(1,200)
        cardTagCrossRefDao.insert(cardTagCrossRef1)
        cardTagCrossRefDao.insert(cardTagCrossRef2)
        cardTagCrossRefDao.insert(cardTagCrossRef3)
//        cardTagCrossRefDao.insert(cardTagCrossRef4)

        val tags = listOf<Long>()

        val result = appRepository.getCardByTagIds(tags)
//        println(result)
        assertThat(result.size, equalTo(0))

    }

    @Test
    @Throws(Exception::class)
    fun getCardByKeywordInSelectedFields() = runBlocking {
        val cardEntity1: CardEntity = CardEntity(
            1, "sam", "unimelb",
            "0922", "444222999", "testr@email.com", "I am a cool guy"
        )
        val cardEntity2: CardEntity = CardEntity(
            2, "jack", "unimelb",
            "0922", "444222999", "test@email.com", "I am a red guy"
        )
        val cardEntity3: CardEntity = CardEntity(
            3, "peter", "unimelb",
            "0922", "444222999", "test@email.com", "I am a blue guy"
        )
        cardEntityDao.insert(cardEntity1)
        cardEntityDao.insert(cardEntity2)
        cardEntityDao.insert(cardEntity3)

        val keyword = "r"
        val columns = listOf("name","description","email")
        val results = appRepository.getCardByKeywordInSelectedColumns(keyword,columns)

        assertThat(results.size, equalTo(3))

    }

    @Test
    @Throws(Exception::class)
    fun getTagsByCardId() = runBlocking {

        /*val result: List<CardEntity> = appRepository.getCardByTagIds()
        println(result)*/

        val tagEntity1:TagEntity = TagEntity(100,"first tag")
        val tagEntity2:TagEntity = TagEntity(200,"second tag")
        tagEntityDao.insert(tagEntity1)
        tagEntityDao.insert(tagEntity2)

        val cardEntity1: CardEntity = CardEntity(
            1, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity2: CardEntity = CardEntity(
            2, "jack", "unimelb",
            "0922", "444222999", "test@email.com", "I am a red guy"
        )
        val cardEntity3: CardEntity = CardEntity(
            3, "peter", "unimelb",
            "0922", "444222999", "test@email.com", "I am a blue guy"
        )
        cardEntityDao.insert(cardEntity1)
        cardEntityDao.insert(cardEntity2)
        cardEntityDao.insert(cardEntity3)

        val cardTagCrossRef1:CardTagCrossRef = CardTagCrossRef(1,100)
//        val cardTagCrossRef2:CardTagCrossRef = CardTagCrossRef(2,100)
        val cardTagCrossRef3:CardTagCrossRef = CardTagCrossRef(2,200)
        val cardTagCrossRef4:CardTagCrossRef = CardTagCrossRef(1,200)
        cardTagCrossRefDao.insert(cardTagCrossRef1)
//        cardTagCrossRefDao.insert(cardTagCrossRef2)
        cardTagCrossRefDao.insert(cardTagCrossRef3)
        cardTagCrossRefDao.insert(cardTagCrossRef4)

        val result = appRepository.getTagsByCardId(1)
        println(result)
        assertThat(result.size, equalTo(2))

    }


    //TODO: implement this test
    /*@Test
    @Throws(Exception::class)
    fun searchCardByTag() = runBlocking{
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity: CardEntity = CardEntity(1,"sam","unimelb",
            "0922","444222999","test@email.com","I am a cool guy")

        cardEntityDao.insert(cardEntity)

        val tagEntity:TagEntity = TagEntity(1)

        val cards = cardEntityDao.getCardByName("Sam");
        println("0930 hello")
        println(cards);

        assertThat(cards[0], equalTo(cardEntity))
    }*/


}