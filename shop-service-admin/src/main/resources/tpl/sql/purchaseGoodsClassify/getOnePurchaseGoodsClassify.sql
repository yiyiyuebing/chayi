SELECT pcy.*,classifyStoreLevel.storeLevelName FROM
purchase_classify pcy LEFT JOIN (SELECT ppc.id ,(SELECT GROUP_CONCAT(sll.name) FROM store_level sll
WHERE
FIND_IN_SET(sll.level_Id,ppc.store_level) AND sll.statue=1) AS storeLevelName FROM purchase_classify ppc)
classifyStoreLevel ON classifyStoreLevel.id = pcy.id
WHERE pcy.del_flag='F' AND 1=1 AND pcy.id= '${ids}'