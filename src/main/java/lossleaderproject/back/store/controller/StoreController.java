package lossleaderproject.back.store.controller;

import lombok.RequiredArgsConstructor;
import lossleaderproject.back.store.dto.StoreRequest;
import lossleaderproject.back.store.entitiy.Store;
import lossleaderproject.back.store.service.StoreService;
import lossleaderproject.back.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    @PostMapping()
    public ResponseEntity<String> newMember(@Valid @RequestBody StoreRequest storeRequest) {

        Long store = storeService.save(storeRequest);
        return ResponseEntity.ok("업체 가입 성공");
    }

    @GetMapping("list/silver/date")
    public ResponseEntity<Page<Store>> findAllSilverDate(@PageableDefault(page = 0,size=10) Pageable pageable) {

        Page<Store> stores = storeService.findAllSilverDate(pageable);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("list/silver/star")
    public ResponseEntity<Page<Store>> findAllSilverStar(@PageableDefault(page = 0,size=10) Pageable pageable) {

        Page<Store> stores = storeService.findAllSilverStar(pageable);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("list/silver/price")
    public ResponseEntity<Page<Store>> findAllSilverPrice(@Valid @RequestParam("sorting") String sorting,@PageableDefault(page = 0,size=10) Pageable pageable) {
        Page<Store> stores;
        if(sorting.equals("DESC")) {
            stores = storeService.findAllSilverPriceDESC(pageable);
        }
        else{
            stores = storeService.findAllSilverPriceASC(pageable);
        }
        return ResponseEntity.ok(stores);
    }

    @GetMapping("list/gold/date")
    public ResponseEntity<Page<Store>> findAllGoldDate(@PageableDefault(page = 0,size=10) Pageable pageable) {

        Page<Store> stores = storeService.findAllGoldDate(pageable);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("list/gold/star")
    public ResponseEntity<Page<Store>> findAllGoldStar(@PageableDefault(page = 0,size=10) Pageable pageable) {

        Page<Store> stores = storeService.findAllGoldStar(pageable);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("list/gold/price")
    public ResponseEntity<Page<Store>> findAllGoldPrice(@Valid @RequestParam("sorting") String sorting,@PageableDefault(page = 0,size=10) Pageable pageable) {
        Page<Store> stores;
        if(sorting.equals("DESC")) {
            stores = storeService.findAllGoldPriceASC(pageable);
        }
        else{
            stores = storeService.findAllGoldPriceDESC(pageable);
        }
        return ResponseEntity.ok(stores);
    }

}
