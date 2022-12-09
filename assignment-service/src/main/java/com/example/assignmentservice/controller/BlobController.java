package com.example.assignmentservice.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.assignmentservice.model.Answer;
import com.example.assignmentservice.service.AnswerService;
import com.example.assignmentservice.service.BlobService;
import com.netflix.discovery.converters.Auto;

@RestController
@RequestMapping("/files")
public class BlobController {

    // @Value("azure-blob://studentassignments/testing")
    // private Resource blobFile;

    // @GetMapping("/readBlobFile")
    // public String readBlobFile() throws IOException {
    // return StreamUtils.copyToString(
    // this.blobFile.getInputStream(),
    // Charset.defaultCharset());
    // }

    // @PostMapping("/writeBlobFile")
    // public String writeBlobFile(@RequestBody String data) throws IOException {
    // try (OutputStream os = ((WritableResource) this.blobFile).getOutputStream())
    // {
    // os.write(data.getBytes());
    // }
    // return "file was updated";
    // }
    @Autowired
    private BlobService azureBlobAdapter;

    @Autowired
    private AnswerService answerService;


    @PostMapping(value = "/{student_id}/{assignment_id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestParam(value = "file") MultipartFile file, @PathVariable("student_id") Integer student_id, @PathVariable("assignment_id") Integer assignment_id)
            throws IOException {

        String url = azureBlobAdapter.upload(file);
        Float score = 0.0f;
        
        Answer persistedAnswer = new Answer(student_id, assignment_id, 1, 0.0f, url);
        answerService.saveAnswer(persistedAnswer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllBlobs() {

        List<String> items = azureBlobAdapter.listBlobs();
        return ResponseEntity.ok(items);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestParam String fileName) {

        azureBlobAdapter.deleteBlob(fileName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile(@RequestParam String fileName)
            throws URISyntaxException {

        ByteArrayResource resource = new ByteArrayResource(azureBlobAdapter
                .getFile(fileName));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""
                        + fileName + "\"");

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(headers).body(resource);
    }

    @GetMapping("{fileName}/url")
    public ResponseEntity<?> findUrl(@PathVariable String fileName){
        String url = azureBlobAdapter.getURL(fileName);
        return new ResponseEntity<String>(url, HttpStatus.OK);
    }
}
