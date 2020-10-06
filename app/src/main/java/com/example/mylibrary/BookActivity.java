package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtBookName, txtAuthor, txtPages, txtDescription;
    private Button btnAddToWantToRead, btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavorite;
    private ImageView bookImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

//        String longDescription = " 1Q84 (いちきゅうはちよん, Ichi-Kyū-Hachi-Yon) is a dystopian novel written by Japanese writer Haruki Murakami, \n" +
//                "first published in three volumes in Japan in 2009–10.[1] It covers a fictionalized year of 1984 in parallel with a \"real\" one. \n" +
//                "The novel is a story of how a woman named Aomame begins to notice strange changes occurring in the world.\n" +
//                " She is quickly enraptured in a plot involving Sakigake, a religious cult, and her childhood love, Tengo, \n" +
//                "and embarks on a journey to discover what is \"real\". Its first printing sold out on the day it was released and \n" +
//                "sales reached a million within a month.[2] The English-language edition of all three volumes, with the first two volumes \n " +
//                "translated by Jay Rubin and the third by Philip Gabriel, was released in North America and the United Kingdom on October 25, 2011.[3][4][5][6]  \n" +
//                "An excerpt from the novel, \"Town of Cats\", appeared in the September 5, 2011 issue of The New Yorker magazine.[7] The first chapter of 1Q84 had also been read \n" +
//                "as an excerpt in the Selected Shorts series at Symphony Space in New York.";
//
//        //TODO: Get the data from recycler view in here
//        Book book = new Book(1, "1Q84", "Haruki Murakami", 1350, "https://3.bp.blogspot.com/-Lujwu7x7Z2A/UMHunYDnPCI/AAAAAAAAB2Q/Fny90q7E5ko/s1600/1Q84+1.jpg"
//                ,"A work of maddening brilliance", longDescription);

        //Always check this
        Intent intent = getIntent();
        if(null != intent)
        {
            //Be careful with this
            int bookId = intent.getIntExtra( BOOK_ID_KEY, -1);
            if (bookId != -1)
            {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (null != incomingBook)
                {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);
                }
            }
        }


    }



    private void handleWantToReadBooks(final Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

        boolean existInWantToReadBooks = false;

        for(Book b: wantToReadBooks)
        {
            if(b.getId() == book.getId()){
                existInWantToReadBooks = true;
            }
        }

        if (existInWantToReadBooks)
        {
            btnAddToWantToRead.setEnabled(false);
        }
        else
        {
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToWantRead(book))
                    {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        btnAddToWantToRead.setEnabled(false);
                        Intent intent = new Intent(BookActivity.this, WantToReadAcitivty.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void handleCurrentlyReadingBooks(final Book book)
    {
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();

        boolean existCurrentReadBooks = false;

        for(Book b: currentlyReadingBooks)
        {
            if(b.getId() == book.getId()){
                existCurrentReadBooks = true;
            }
        }

        if (existCurrentReadBooks)
        {
            btnAddToCurrentlyReading.setEnabled(false);
        }
        else
        {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToCurrentRead(book))
                    {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        btnAddToCurrentlyReading.setEnabled(false);
                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleFavoriteBooks(final Book book) {
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();

        boolean existInFavoriteBooks = false;

        for(Book b: favoriteBooks)
        {
            if(b.getId() == book.getId()){
                existInFavoriteBooks = true;
            }
        }

        if (existInFavoriteBooks)
        {
            btnAddToFavorite.setEnabled(false);
        }
        else
        {
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToFavoriteRead(book))
                    {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        btnAddToFavorite.setEnabled(false);
                        Intent intent = new Intent(BookActivity.this, FavoritesActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Enable and Disable Button,
     * Add the book to Already Read Book ArrayList
     * @param book
     */
    private void handleAlreadyRead(final Book book){
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

            boolean existInAlreadyReadBook = false;

            for(Book b: alreadyReadBooks)
            {
                if(b.getId() == book.getId()){
                    existInAlreadyReadBook = true;
                }
            }

            if (existInAlreadyReadBook)
            {
                btnAddToAlreadyRead.setEnabled(false);
            }
            else
            {
                btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utils.getInstance(BookActivity.this).addToAlreadyRead(book))
                        {
                            Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                            btnAddToAlreadyRead.setEnabled(false);
                            Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }

    private void setData(Book book){
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(bookImage);

    }

    //be Careful with Ids
    private void initViews()
    {
        txtAuthor = findViewById(R.id.txtAuthorName);
        txtBookName = findViewById(R.id.txtBookName);
        txtPages = findViewById(R.id.txtPages);
        txtDescription = findViewById(R.id.txtDescription);

        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyReadList);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToReadList);

        bookImage = findViewById(R.id.imgBook);
    }
}