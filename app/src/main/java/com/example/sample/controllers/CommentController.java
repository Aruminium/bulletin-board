package com.example.sample.controllers;

import com.example.sample.entities.Comment;
import com.example.sample.forms.CommentCreateForm;
import com.example.sample.repositories.ICommentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CommentController {
    private final ICommentRepository commentRepository;

    public CommentController(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("/comments")
    public String commentList(Model model) {
        model.addAttribute("title", "発言一覧");
        model.addAttribute("comments", commentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")));
        model.addAttribute("main", "comments/commentList::main");

        return "layout/base";
    }

    @GetMapping("/comments/create")
    public String commentCreate(Model model, @ModelAttribute CommentCreateForm commentCreateForm) {
        model.addAttribute("title", "新規発言を追加");
        model.addAttribute("commentCreateForm", commentCreateForm);
        model.addAttribute("main", "comments/commentCreate::main");

        return "layout/base";
    }

    @PostMapping("/comments/create")
    public String commentCreate(Model model, @Valid CommentCreateForm commentCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return commentCreate(model, commentCreateForm);
        }

        Comment comment = new Comment();
        comment.setName(commentCreateForm.getName());
        comment.setContent(commentCreateForm.getContent());
        commentRepository.save(comment);

        return "redirect:/comments";
    }

    @GetMapping("/comments/detail/{id}")
    public String commentDetail(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("title", String.format("id: %d の発言", id));
        model.addAttribute("comment", commentRepository.findById(id).orElseThrow());
        model.addAttribute("main", "comments/commentDetail::main");

        return "layout/base";
    }

    @GetMapping("/comments/update/{id}")
    public String commentUpdate(Model model, @PathVariable("id") Integer id, @ModelAttribute CommentCreateForm commentCreateForm) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        commentCreateForm.setName(comment.getName());
        commentCreateForm.setContent(comment.getContent());

        model.addAttribute("title", String.format("id: %d の編集", id));
        model.addAttribute("id", id);
        model.addAttribute("commentCreateForm", commentCreateForm);
        model.addAttribute("main", "comments/commentUpdate::main");

        return "layout/base";
    }

    @PostMapping("/comments/update/{id}")
    public String commentUpdate(Model model, @PathVariable("id") Integer id, @Valid CommentCreateForm commentCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return commentUpdate(model, id, commentCreateForm);
        }

        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.setContent(commentCreateForm.getContent());
        comment.setName(commentCreateForm.getName());
        commentRepository.save(comment);

        return "redirect:/comments/detail/" + id;
    }

    @PostMapping("/comments/delete/{id}")
    public String commentDelete(@PathVariable Integer id){
        commentRepository.deleteById(id);
        return "redirect:/comments";
    }
}
