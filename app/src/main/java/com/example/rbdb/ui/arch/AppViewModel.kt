package com.example.rbdb.ui.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.*
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    private lateinit var repository: AppRepository

    fun init(appDatabase: AppDatabase) {
        repository = AppRepository(appDatabase)
    }

    // Call repository methods for card
    fun insertCard(cardEntity: CardEntity): LiveData<Long>{
        val result = MutableLiveData<Long>()
        viewModelScope.launch { result.postValue(repository.insertCard(cardEntity)) }
        return result
    }

    fun deleteCard(cardEntity: CardEntity) {
        viewModelScope.launch { repository.deleteCard(cardEntity) }
    }

    fun deleteCardAndCrossRefByCardId(cardId: Long) {
        viewModelScope.launch { repository.deleteCardAndCrossRefByCardId(cardId) }
    }

    fun updateCard(cardEntity: CardEntity) {
        viewModelScope.launch { repository.updateCard(cardEntity) }
    }

    fun getCardById(id:Long): LiveData<CardEntity>{
        val result = MutableLiveData<CardEntity>()
        viewModelScope.launch { result.postValue(repository.getCardById(id)) }
        return result
    }

    fun getAllCards(): LiveData<List<CardEntity>> {
        val result = MutableLiveData<List<CardEntity>>()
        viewModelScope.launch { result.postValue(repository.getAllCards()) }
        return result
    }

    fun getCardsInList(id:Long): LiveData<List<CardEntity>> {
        val result = MutableLiveData<List<CardEntity>>()
        viewModelScope.launch { result.postValue(repository.getListWithCardsByListId(id).cards) }
        return result
    }

    fun getCardWithTags(): LiveData<List<CardWithTagsEntity>> {
        val result = MutableLiveData<List<CardWithTagsEntity>>()
        viewModelScope.launch { result.postValue(repository.getCardWithTags()) }
        return result
    }

    fun getCardWithLists(): LiveData<List<CardWithListsEntity>> {
        val result = MutableLiveData<List<CardWithListsEntity>>()
        viewModelScope.launch { result.postValue(repository.getCardWithLists()) }
        return result
    }

    fun getCardsByName(input : String): LiveData<List<CardEntity>> {
        val result = MutableLiveData<List<CardEntity>>()
        viewModelScope.launch { result.postValue(repository.getCardsByName(input)) }
        return result
    }

    fun getCardByKeywordInSelectedColumns(keyword: String, columns: List<String>): LiveData<List<CardEntity>>{
        val result = MutableLiveData<List<CardEntity>>()
        viewModelScope.launch { result.postValue(repository.getCardByKeywordInSelectedColumns(keyword, columns)) }
        return result
    }

    // Call repository methods for list
    fun getListById(id: Long): LiveData<ListEntity> {
        val result = MutableLiveData<ListEntity>()
        viewModelScope.launch { result.postValue(repository.getListById(id)) }
        return result
    }
    fun insertList(listEntity: ListEntity):LiveData<Long> {
        val result = MutableLiveData<Long>()
        viewModelScope.launch { result.postValue(repository.insertList(listEntity)) }
        return result
    }

    fun deleteList(listEntity: ListEntity) {
        viewModelScope.launch { repository.deleteList(listEntity) }
    }

    fun deleteListAndCrossRefByListId(listId: Long) {
        viewModelScope.launch { repository.deleteListAndCrossRefByListId(listId) }
    }

    fun updateList(listEntity: ListEntity) {
        viewModelScope.launch { repository.updateList(listEntity) }
    }

    fun updateListName(name: String, listId: Long) {
        viewModelScope.launch { repository.updateListName(name, listId)}
    }

    fun getAllLists(): LiveData<List<ListEntity>> {
        val result = MutableLiveData<List<ListEntity>>()
        viewModelScope.launch { result.postValue(repository.getAllLists()) }
        return result
    }

    fun getListWithCards(): LiveData<List<ListWithCardsEntity>> {
        val result = MutableLiveData<List<ListWithCardsEntity>>()
        viewModelScope.launch { result.postValue(repository.getListWithCards()) }
        return result
    }

    fun insertCardToList(crossRef: CardListCrossRef) {
        viewModelScope.launch { repository.insertCardListCrossRef(crossRef)}
    }

    // Call repository methods for tag
    fun insertTag(tagEntity: TagEntity) {
        viewModelScope.launch { repository.insertTag(tagEntity) }
    }

    fun deleteTag(tagEntity: TagEntity) {
        viewModelScope.launch { repository.deleteTag(tagEntity) }
    }

    fun deleteTagAndCrossRefByTagId(tagId: Long) {
        viewModelScope.launch { repository.deleteTagAndCrossRefByTagId(tagId) }
    }

    fun updateTag(tagEntity: TagEntity) {
        viewModelScope.launch { repository.updateTag(tagEntity) }
    }

    fun getAllTags(): LiveData<List<TagEntity>> {
        val result = MutableLiveData<List<TagEntity>>()
        viewModelScope.launch { result.postValue(repository.getAllTags()) }
        return result
    }

    fun getTagWithCards(): LiveData<List<TagWithCardsEntity>> {
        val result = MutableLiveData<List<TagWithCardsEntity>>()
        viewModelScope.launch { result.postValue(repository.getTagWithCards()) }
        return result
    }

    fun getTagsByCardId(cardId: Long): LiveData<List<TagEntity>> {
        val result = MutableLiveData<List<TagEntity>>()
        viewModelScope.launch { result.postValue(repository.getTagsByCardId(cardId)) }
        return result
    }

    fun getTagID(nameOfTag: String): LiveData<Long> {
        val result = MutableLiveData<Long>()
        viewModelScope.launch { result.postValue(repository.getTagID(nameOfTag)) }
        return result
    }

    fun getCardByTagIds(tagIds: ArrayList<Long>): LiveData<List<CardEntity>> {
        val result = MutableLiveData<List<CardEntity>>()
        viewModelScope.launch { result.postValue(repository.getCardByTagIds(tagIds))}
        return result
    }

    // Call repository methods for user
    fun insertUser(userEntity: UserEntity) {
        viewModelScope.launch { repository.insertUser(userEntity) }
    }

    fun deleteUser(userEntity: UserEntity) {
        viewModelScope.launch { repository.deleteUser(userEntity) }
    }

    fun updateUser(userEntity: UserEntity) {
        viewModelScope.launch { repository.updateUser(userEntity) }
    }

    fun getAllUsers(): LiveData<List<UserEntity>> {
        val result = MutableLiveData<List<UserEntity>>()
        viewModelScope.launch { result.postValue(repository.getAllUsers()) }
        return result
    }

    // Call repository methods for CardListCrossRefs
    fun deleteAllCrossRefByListId(listId: Long) {
        viewModelScope.launch {repository.deleteAllCrossRefByListId(listId)}
    }

    // Call repository methods for CardTagCrossRefs
    fun insertCardTagCrossRef(cardTagCrossRef: CardTagCrossRef) {
        viewModelScope.launch {repository.insertCardTagCrossRef(cardTagCrossRef)}
    }

    fun deleteAllTagCrossRefByCardId(cardId: Long) {
        viewModelScope.launch {repository.deleteAllTagCrossRefByCardId(cardId)}
    }
}