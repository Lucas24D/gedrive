package com.ldsa.gedrive.controllers;

import com.ldsa.gedrive.dtos.GoogleDriveFolderDTO;
import com.ldsa.gedrive.services.GoogleDriveFolderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/folders")
@RequiredArgsConstructor
@Tag(name = "Folder", description = "Folders API")
public class GoogleDriveFolderController {

    private final GoogleDriveFolderService googleDriveFolderService;

    @GetMapping
    public ResponseEntity<List<GoogleDriveFolderDTO>> findAll() {
        return ResponseEntity.ok(googleDriveFolderService.findAll());
    }

    @GetMapping("/{folderName}")
    public String getFolderId(@PathVariable String folderName) {
        return googleDriveFolderService.getFolderId(folderName);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam("folderName") String folderName) {
        googleDriveFolderService.create(folderName);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        googleDriveFolderService.delete(id);
    }

    @GetMapping(value = "/download/{id}", produces = "application/zip")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<byte[]> download(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        String filename = String.format("%s.zip", id);
        headers.add("Content-Disposition", "inline; filename=" + filename);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.valueOf("application/zip")).body(googleDriveFolderService.download(id));
    }

    @PostMapping("/{folderId}/permission/{gmail}")
    @ResponseStatus(HttpStatus.OK)
    public void permission(@PathVariable String folderId, @PathVariable String gmail) {
        googleDriveFolderService.shareFolder(folderId, gmail);
    }
}
