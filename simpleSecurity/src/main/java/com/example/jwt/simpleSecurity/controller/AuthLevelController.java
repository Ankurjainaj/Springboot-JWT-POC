package com.example.jwt.simpleSecurity.controller;

import com.example.jwt.simpleSecurity.exception.EntityNotFoundException;
import com.example.jwt.simpleSecurity.model.AuthLevel;
import com.example.jwt.simpleSecurity.model.User;
import com.example.jwt.simpleSecurity.repository.AuthLevelRepository;
import com.example.jwt.simpleSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/authLevel")
@Validated
class AuthLevelController {

//    @Autowired
    private AuthLevelRepository repository;
//    @Autowired
    private UserRepository userRepository;

//    AuthLevelController(AuthLevelRepository repository, UserRepository userRepository) {
//        this.repository = repository;
//        this.userRepository = userRepository;
//    }

    @GetMapping
    Page<AuthLevel> all(Pageable pageable, OAuth2Authentication authentication) {
        String auth = (String) authentication.getUserAuthentication().getPrincipal();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals(User.Role.USER.name())) {
            User user = userRepository.findByEmail(auth).orElseThrow(() -> new EntityNotFoundException(User.class, "email", auth));
            return repository.findAllByUser(user, pageable);
        }
        return repository.findAll(pageable);
    }

    @GetMapping("/search")
    Page<AuthLevel> search(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "toDate", required = false) LocalDate toDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
            @RequestParam(value = "fromTime", required = false) LocalTime fromTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
            @RequestParam(value = "toTime", required = false) LocalTime toTime,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "value", required = false) Double cal,
            @RequestParam(value = "userId", required = false) String userId,
            Pageable pageable, OAuth2Authentication authentication) {
        String auth = (String) authentication.getUserAuthentication().getPrincipal();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals(User.Role.USER.name())) {
            User user = userRepository.findByEmail(auth).orElseThrow(() -> new EntityNotFoundException(User.class, "email", auth));
            userId = user.getId();
            return repository.filter(fromDate, toDate, fromTime, toTime, text, cal, userId, pageable);
        }
        return repository.filter(fromDate, toDate, fromTime, toTime, text, cal, userId, pageable);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('ADMIN') || (returnObject.user == @userRepository.findByEmail(authentication.principal).get())")
    AuthLevel one(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || (@gleeRepository.findById(#id).orElse(new net.reliqs.gleeometer.glee.Glee()).user == @userRepository.findByEmail(authentication.principal).get())")
    void update(@PathVariable String id, @Valid @RequestBody AuthLevel res) {
        if (repository.existsById(id)) {
            repository.save(res);
        } else {
            throw new EntityNotFoundException(User.class, "id", id);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') || (#res != null && #res.user.id == @userRepository.findByEmail(authentication.principal).get().id)")
    AuthLevel create(@Valid @RequestBody AuthLevel res) {
        return repository.save(res);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || (@gleeRepository.findById(#id).orElse(new net.reliqs.gleeometer.glee.Glee()).user == @userRepository.findByEmail(authentication.principal).get())")
    void delete(@PathVariable String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException(User.class, "id", id);
        }
    }

}
