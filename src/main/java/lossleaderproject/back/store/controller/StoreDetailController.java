package lossleaderproject.back.store.controller;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.store.entitiy.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store-detail")
public class StoreDetailController {

//    @GetMapping("list/silver/date")
//    public ResponseEntity<Page<Store>> findOneStoreDetail(@PageableDefault(page = 0,size=10) Pageable pageable) {
//
//        //Page<Store> stores = storeService.findAllSilverDate(pageable);
//        return ResponseEntity.ok(stores);
//    }
}
