package org.zerock.springboot.item.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.springboot.item.dto.ItemDTO;
import org.zerock.springboot.item.dto.ItemFormDto;
import org.zerock.springboot.item.dto.ItemImgDto;
import org.zerock.springboot.item.dto.ItemSearchDto;
import org.zerock.springboot.item.entity.Item;
import org.zerock.springboot.item.entity.ItemImg;
import org.zerock.springboot.item.repository.ItemImgRepository;
import org.zerock.springboot.item.repository.ItemRepository;
import org.zerock.springboot.main.dto.MainItemDto;
import org.zerock.springboot.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto,
                         List<MultipartFile> itemImgFileList)throws Exception{

        //상품 등록
        Item item = itemFormDto.createItem();
        Member register = Member.builder().id(itemFormDto.getRegister()).build();
        item.setRegister(register);
        itemRepository.save(item);

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i == 0){
                itemImg.setRepimgYn("Y");
            }else {
                itemImg.setRepimgYn("N");
            }
                itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

            return item.getId();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){
        List<ItemImg> itemImgList =
                itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for(ItemImg itemImg : itemImgList){
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Optional<Item> result = itemRepository.findById(itemId);
        Item item= result.get();
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setRegister(item.getRegister().getId());
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    /*
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList)throws Exception{
        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();
        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto,
                                       Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    */
    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

	public ItemDTO getItem(Long itemId) {
		Optional<Item> result = itemRepository.findById(itemId);
		if(result.isPresent()) {
			Item item = result.get();
			ItemDTO dto = entityToDTO(item);
			return dto;
		}
		return null;
	}

	private ItemDTO entityToDTO(Item item) {
		ItemDTO itemDTO = ItemDTO.builder()
				.itemId(item.getId())
				.registerId(item.getRegister().getId())
				.category(item.getCategory())
				.delivery(item.getDelivery())
				.itemNm(item.getItemNm())
				.price(item.getPrice())
				.regDate(item.getRegDate())
				.modDate(item.getModDate())
				.build();
		return itemDTO;
	}
}
