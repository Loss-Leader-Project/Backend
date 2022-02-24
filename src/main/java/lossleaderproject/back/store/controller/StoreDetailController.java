package lossleaderproject.back.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store-detail")
public class StoreDetailController {

//    @GetMapping()
//    public ResponseEntity<Page<Store>> findOneStoreDetail(@PageableDefault(page = 0,size=10) Pageable pageable) {
//
//        //Page<Store> stores = storeService.findAllSilverDate(pageable);
//        return ResponseEntity.ok(stores);
//    }
}