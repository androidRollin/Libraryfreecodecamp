package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String ALL_BOOKS_KEY ="all_books";
    private static final String ALREADY_READ_BOOKS = "already_read_books";
    private static final String WANT_TO_READ_BOOK = "want_to_read_books";
    private static final String CURRENTLY_READING_BOOKS = "currently_reading_books";
    private static final String FAVORITE_BOOKS = "favorite_books";

    private static Utils instance;

    private SharedPreferences sharedPreferences;

//    private static ArrayList<Book> allBooks;
//    private static ArrayList<Book> alreadyReadBooks;
//    private static ArrayList<Book> wantToReadBooks;
//    private static ArrayList<Book> currentlyReadingBooks;
//    private static ArrayList<Book> favoriteBooks;


    private Utils(Context context) {



        sharedPreferences = context.getSharedPreferences("alternate_db",Context.MODE_PRIVATE);

        if (null == getAllBooks() )
        {
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyReadBooks())
        {
            editor.putString(ALREADY_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getWantToReadBooks())
        {
            editor.putString(WANT_TO_READ_BOOK, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getCurrentlyReadingBooks())
        {
            editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getFavoriteBooks())
        {
            editor.putString(FAVORITE_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

    }


    private void initData() {
        //TODO: add initial data

        ArrayList<Book> books = new ArrayList<>();
        String longDescription = " 1Q84 (いちきゅうはちよん, Ichi-Kyū-Hachi-Yon) is a dystopian novel written by Japanese writer Haruki Murakami, \n" +
                "first published in three volumes in Japan in 2009–10.[1] It covers a fictionalized year of 1984 in parallel with a \"real\" one. \n" +
                "The novel is a story of how a woman named Aomame begins to notice strange changes occurring in the world.\n" +
                " She is quickly enraptured in a plot involving Sakigake, a religious cult, and her childhood love, Tengo, \n" +
                "and embarks on a journey to discover what is \"real\". Its first printing sold out on the day it was released and \n" +
                "sales reached a million within a month.[2] The English-language edition of all three volumes, with the first two volumes \n " +
                "translated by Jay Rubin and the third by Philip Gabriel, was released in North America and the United Kingdom on October 25, 2011.[3][4][5][6]  \n" +
                "An excerpt from the novel, \"Town of Cats\", appeared in the September 5, 2011 issue of The New Yorker magazine.[7] The first chapter of 1Q84 had also been read \n" +
                "as an excerpt in the Selected Shorts series at Symphony Space in New York.";
        books.add(new Book(1, "1Q84", "Haruki Murakami", 1350, "https://3.bp.blogspot.com/-Lujwu7x7Z2A/UMHunYDnPCI/AAAAAAAAB2Q/Fny90q7E5ko/s1600/1Q84+1.jpg"
                ,"A work of maddening brilliance", longDescription));
        books.add(new Book(2, "The Curious Incident of the Dog in the Night-Time", "Mark Haddon", 274, "https://kbimages1-a.akamaihd.net/263d22cf-9a37-4872-b34f-13e3fb2c7ccb/353/569/90/False/the-curious-incident-of-the-dog-in-the-night-time-3.jpg"
                ,"The Curious Incident of the Dog in the Night-Time is a 2003 mystery novel by British writer Mark Haddon.", "Long Description"));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit(); //editor.apply()



    }

    public static Utils getInstance(Context context) {
        if (null != instance)
        {
            return instance;
        }
        else
        {
            instance = new Utils(context);
            return instance;
        }
    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType(); //defining the object kind
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType(); //defining the object kind
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS, null), type);
        return books;
    }

    public ArrayList<Book> getWantToReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType(); //defining the object kind
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOK, null), type);
        return books;
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType(); //defining the object kind
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS, null), type);
        return books;
    }

    public ArrayList<Book> getFavoriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType(); //defining the object kind
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS, null), type);
        return books;
    }

    public Book getBookById(int id)
    {
        ArrayList<Book> books = getAllBooks();
        if( null != books)
        {
            for (Book b: books)
            {
                if (b.getId() == id )
                    return b;
            }

        }

        return null;
    }

    public boolean addToAlreadyRead(Book book)
    {
        ArrayList<Book> books = getAlreadyReadBooks();
        if (null != books)
        {
            if(books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS);
                editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToWantRead(Book book)
    {
        ArrayList<Book> books = getWantToReadBooks();
        if (null != books)
        {
            if(books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOK);
                editor.putString(WANT_TO_READ_BOOK, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurrentRead(Book book)
    {
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if (null != books)
        {
            if(books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS);
                editor.putString(CURRENTLY_READING_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavoriteRead(Book book)
    {
        ArrayList<Book> books = getFavoriteBooks();
        if (null != books)
        {
            if(books.add(book))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITE_BOOKS);
                editor.putString(FAVORITE_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }
    public boolean removeFromAlreadyRead(Book book)
    {
        ArrayList<Book> books = getAlreadyReadBooks();
        if (null != books)
        {
            for ( Book b: books)
            {
                if( b.getId() == book.getId())
                {
                    if (books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_BOOKS);
                        editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean removeFromWantToRead(Book book)
    {
        ArrayList<Book> books = getWantToReadBooks();
        if (null != books)
        {
            for ( Book b: books)
            {
                if( b.getId() == book.getId())
                {
                    if (books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_BOOK);
                        editor.putString(WANT_TO_READ_BOOK, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean removeFromCurrentlyReading(Book book)
    {
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if (null != books)
        {
            for ( Book b: books)
            {
                if( b.getId() == book.getId())
                {
                    if (books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS);
                        editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean removeFromFavorites(Book book)
    {
        ArrayList<Book> books = getFavoriteBooks();
        if (null != books)
        {
            for ( Book b: books)
            {
                if( b.getId() == book.getId())
                {
                    if (books.remove(b))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVORITE_BOOKS);
                        editor.putString(FAVORITE_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
