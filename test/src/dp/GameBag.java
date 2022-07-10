package dp;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 游戏背包的代码实现(动态规划最优解)
 */
public class GameBag {
    private static class ArrayElement{
        /**
         * 计算后数组元素的值
         */
        private int value;
        /**
         * 哪些物品的值组成了当前数组元素
         */
        private Set<Element> elements;

        public ArrayElement(int value, Set<Element> elements) {
            this.value = value;
            this.elements = elements;
        }

        public ArrayElement(int value, Element element) {
            this.value = value;
            this.elements = Sets.newHashSet();
            elements.add(element);
        }

        @Override
        public String toString() {
            return "ArrayElement{" +
                    "value=" + value +
                    ", elements=" + elements +
                    '}';
        }
    }

    public static void printRow(int rowNo, int bagSize, ArrayElement[][] calc){
        System.out.println("当前行号: " + rowNo);
        for(int j = 0; j < bagSize; j ++){
            if(calc[rowNo][j] != null){
                System.out.print("j = " + j + "," + calc[rowNo][j] + " ");
            }
        }
        System.out.println();
    }

    public static void putBag(Element[] goods, int bagSize){
        ArrayElement[][] calcArray = new ArrayElement[goods.length][bagSize];
        for(int i = 0; i < goods.length; i ++){
            for (int j = 0; j < bagSize; j ++){
                if(i == 0){
                    // 第一行只有一个物品，则都放这个物品
                    calcArray[i][j] = new ArrayElement(goods[i].value, goods[i]);
                }else{
                    // 计算单元格是否放得下
                    int spareSpace = j + 1 - goods[i].cost;
                    if(spareSpace < 0){
                        // 放不下 直接放上一行同一列的值
                        calcArray[i][j] = calcArray[i - 1][j];
                    }else{
                        // 判断下上一行同一列的值跟当前商品的价值 + 剩余空间的价值哪个更大
                        int preRow = i - 1;// 上一个行的下标
                        int preRowValue = calcArray[preRow][j].value;// 上一行同一列的值
                        int currentGoodsValue = goods[i].value;// 当前商品的价值
                        if(spareSpace > 0){
                            // 还有剩余空间        当前的价值 + 剩余空间的最大价值 对应的就是上一行的下标(剩余空间 - 1)
                            currentGoodsValue += calcArray[preRow][spareSpace - 1].value;
                        }
                        if(currentGoodsValue < preRowValue){
                            // 如果当前价值小于上一行同一列的商品价值 直接使用上一行同一列的组合
                            calcArray[i][j] = calcArray[preRow][j];
                        }else{
                            // 当前价值大于上一个商品的价值，有两种情况 1.当前一个商品的价值就已经超过上列的组合 2.当前商品跟之前商品的新组合
                            if(spareSpace == 0){
                                // 如果剩余空间只够放当前物品
                                calcArray[i][j] = new ArrayElement(currentGoodsValue, goods[i]);
                            }else{
                                // 全新的组合
                                Set<Element> newElement = Sets.newHashSet(calcArray[preRow][spareSpace - 1].elements);
                                newElement.add(goods[i]);
                                calcArray[i][j] = new ArrayElement(currentGoodsValue, newElement);
                            }
                        }
                    }
                }
            }
            printRow(i, bagSize, calcArray);

        }
        System.out.println("最终结果 ： " + calcArray[goods.length - 1][bagSize - 1]);
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

    private static class Element{
        private final String name;

        private final int value;

        private final int cost;

        public Element(String name, int value, int cost) {
            this.name = name;
            this.value = value;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    ", cost=" + cost +
                    '}';
        }
    }
}
