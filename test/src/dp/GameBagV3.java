package dp;

public class GameBagV3 {
    private static class Element{
        private final String name;
        private final int value;
        private final int cost;

        public Element(String name, int value, int cost) {
            this.name = name;
            this.value = value;
            this.cost = cost;
        }
    }
    public static void putBag(Element[] goods, int bagSize){
        int[] calcArray = new int[bagSize + 1];
        calcArray[0] = 0;
        for(int goodsIndex = 0; goodsIndex < goods.length; goodsIndex ++){
            int goodsCost = goods[goodsIndex].cost;
            System.out.println(goods[goodsIndex].name + " : 空间耗费 : " + goodsCost + ", 价值 : " + goods[goodsIndex].value);
            for (int currentSize = bagSize; currentSize > 0 ; currentSize--) {
                System.out.println("当前背包大小 : " + currentSize);
                if(currentSize >= goodsCost){
                    calcArray[currentSize] = Math.max(calcArray[currentSize - goodsCost] + goods[goodsIndex].value, calcArray[currentSize]);
                }
            }
            System.out.println("------------");
        }
        System.out.println("最终结果 : " + calcArray[bagSize]);
    }

    public static void main(String[] args) {
        Element[] gameElements = {new Element("段誉匕", 1500, 1), new Element("金晶渔婆见", 2000, 3), new Element("天蝉甲", 3000, 4)};
        putBag(gameElements, 4);

        Element[] gameElements1 = {new Element("天安门广场", 9, 1)
                , new Element("故宫", 9, 4)
                , new Element("颐和园", 9, 2)
                , new Element("八达岭", 7, 1)
                , new Element("天坛", 6, 1)
                , new Element("圆明园", 8, 2)
                , new Element("恭王府", 5, 1)};
        putBag(gameElements1, 6);
    }
}
