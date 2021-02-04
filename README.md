## 1 树状结构数据库 demo 详见 demo.sql
## 2 model 说明:
### 2.1 TreeNodeConvert 
    实体类转化器，用以各实体类间的转化
### 2.2 TreeNodeForm    
    实体类表单对象，用以新增和更新时使用
### 2.3 TreeNode        
    实体类对象
### 2.4 TreeNodeVo      
    实体类展示层，用以展示树状结构
## 3 使用说明

### 3.1 继承 TreeNodeBaseService 类

```@Service
 public class TreeService extends TreeNodeBaseService<Tree, TreeForm, TreeVo> {
 
     @Autowired
     private TreeDao dao;
 
     @Override
     public TreeNodeConvert<Tree, TreeForm, TreeVo> generateConvert() {
         return new TreeNodeConvert<>(new Tree(), new TreeForm(), new TreeVo());
     }
 
     @Override
     public Tree queryById(Long id) {
         TreeQuery query = new TreeQuery();
         query.setId(id);
         List<Tree> list = dao.queryList(query);
         if (CollectionUtils.isEmpty(list)) {
             return null;
         }
         return list.get(0);
     }
 
     @Override
     public Tree queryByCode(String code) {
         TreeQuery query = new TreeQuery();
         query.setCode(code);
         List<Tree> list = dao.queryList(query);
         if (CollectionUtils.isEmpty(list)) {
             return null;
         }
         return list.get(0);
     }
 
     @Override
     public List<Tree> queryByPid(Long pId) {
         TreeQuery query = new TreeQuery();
         query.setPId(pId);
         return dao.queryList(query);
     }
 
     @Override
     public List<Tree> getAll() {
         return dao.queryList(new TreeQuery());
     }
 
     @Override
     public List<Tree> queryLikeCode(String code) {
         return dao.queryLikeCode(code);
     }
 
     @Override
     public long insert(Tree tree) {
         return dao.save(tree);
     }
 
     @Override
     public long updateEntity(Tree tree) {
         return dao.update(tree);
     }
 
     @Override
     public Tree initRoot() {
         Tree root = new Tree();
         root.setId(getParentId());
         root.setPId(-1L);
         root.setName("root");
         root.setCode("100000000000000000");
         dao.save(root);
         return root;
     }
 }
```
### 3.2 继承 TreeNodeCacheService

    此类与 TreeNodeBaseService 相似，在此基础上对获取树结构添加缓存机制
    
## 4 实现的方法

### 4.1 save
    保存一条记录，code 会根据规则自动生成

### 4.2 update
    更新一条记录，目前只会更新 name
    
### 4.3 tree
    获取树状结构，返回包含根节点在内

### 4.4 getRoot
    获取根节点

### 4.5 getRootPath
    根据当前节点获取到根节点的路径

### 4.6 getChildNode
    获取当前节点的所有子节点
    
## 5 其他注意事项

### 5.1 code 有特殊含义，可以优化查询过程
### 5.2 实体类转换器可以用默认的，也可以自定义转换方式
### 5.3 queryLikeCode 方法为右模糊查询