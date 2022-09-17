package com.ukim.finki.learn2cookbackend.service;

import com.ukim.finki.learn2cookbackend.model.Receipt;
import com.ukim.finki.learn2cookbackend.model.ReceiptDone;
import com.ukim.finki.learn2cookbackend.model.response.ThumbSnapResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class CertificateService {

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RecipesService recipesService;

    @Value("${IMAGE_LINK}")
    private String link;

    @Value("${IMAGE_API}")
    private String apiKey;

    public ReceiptDone createReceiptDone(Long receiptId, MultipartFile multipartFile) {
        ThumbSnapResponse response = uploadPicture(multipartFile);

        Receipt receipt = recipesService.getReceipt(receiptId);

        return ReceiptDoneFactory(receipt, response.getData().getMedia());
    }

    private ThumbSnapResponse uploadPicture(MultipartFile image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("media", image.getResource());
        map.add("key", apiKey);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);

        return restTemplate.exchange(
                link, HttpMethod.POST, entity, ThumbSnapResponse.class).getBody();
    }

    private ReceiptDone ReceiptDoneFactory(Receipt receipt, String imageUrl) {
        ReceiptDone receiptDone = new ReceiptDone();
        receiptDone.setReceipt(receipt);
        receiptDone.setImageUrl(imageUrl);
        receiptDone.setDate(LocalDateTime.now());

        return receiptDone;
    }
}
