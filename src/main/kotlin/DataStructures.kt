class Note(val title:String, val body:String) {
    override fun toString():String {
        return title
    }
}

class Archive(val title:String) {
    val notes: ArrayList<Note> = ArrayList()

    fun addNote(title:String, body:String) {
        notes.add(Note(title, body))
    }

    override fun toString(): String {
        return title
    }
}

