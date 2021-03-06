package com.capstone.kitsune.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "blog_title", nullable = false, length = 100)
    private String blogTitle;

    @Column(nullable = false, length = 100)
    private String handle;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    // Please review: Original SQL file did not have a list of posts in a blog. Remove if necessary
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_id")
    private List<Post> posts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany()
    @JoinTable(
            name = "blogs_categories",
            joinColumns = {@JoinColumn(name = "blog_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Category> categories;

    @ManyToMany(mappedBy ="following")
    private List<User> followers;

    //Empty Constructor
    public Blog() {
    }

    //Full Constructor
    public Blog(long id, String blogTitle, String handle, Date dateCreated, List<Post> posts, User user, List<Category> categories, List<User> followers) {
        this.id = id;
        this.blogTitle = blogTitle;
        this.handle = handle;
        this.dateCreated = dateCreated;
        this.posts = posts;
        this.user = user;
        this.categories = categories;
        this.followers = followers;
    }

    //My Blog Constructor
    public Blog(String blogTitle, List<Post> posts){
        this.blogTitle = blogTitle;
        this.posts = posts;
    }

    //Constructor for testing BlogController (create)
    public Blog(String blogTitle, String handle){
        this.blogTitle = blogTitle;
        this.handle = handle;
    }
    //Constructor for testing BlogController (create)
    public Blog(String blogTitle, String handle, User user){
        this.blogTitle = blogTitle;
        this.handle = handle;
        this.user = user;
    }


    //Create constructor
    public Blog(String blogTitle, String handle, Date dateCreated, List<Category> categories){
        this.blogTitle = blogTitle;
        this.handle = handle;
        this.dateCreated = dateCreated;
        this.categories = categories;
    }

    //Edit Constructor
    public Blog(String blogTitle, String handle, List<Category> categories){
        this.blogTitle = blogTitle;
        this.handle = handle;
        this.categories = categories;
    }
    public List<User> getFollowers(){
        return this.followers;
    }

    public void setFollowers(List<User> followers){
        this.followers = followers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
