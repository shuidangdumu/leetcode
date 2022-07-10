package dp;

public class GameBagV2 {
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

    private void printArray(int arrayX, int arrayY, int[][] calcArray){
        for (int i = 0; i < arrayX; i++){
            for(int j = 0; j < arrayY; j++){
                System.out.print("[i=" + i + ",j=" + j + "]=" + calcArray[i][j]+" ,");
            }
            System.out.println();
        }
    }

    public static void printRow(int rowNo, int[][] calcArray){
        System.out.println("当前行号: " + rowNo);
        int arrayY = 0;
        for (int j : calcArray[rowNo]){
            System.out.print("[arrayY=" + arrayY++ + "]=" + j + " ,");
        }
        System.out.println();
    }

    public static void putBag(Element[] goods, int bagSize){
        int arrayX = goods.length + 1;
        int arrayY = bagSize + 1;
        int[][] calcArray = new int[arrayX][arrayY];
        for (int i = 0; i < arrayX; i++) {
            for (int j = 0; j < arrayY; j++) {
                if(i == 0 || j == 0){
                    // 第0行 第0列 赋0值
                    calcArray[i][j] = 0;
                }else{
                    // 当前物品在物品数组的下标
                    int goodsIndex = i - 1;
                    // 上一行同列的值
                    int preRow = i - 1;
                    int preRowValue = calcArray[preRow][j];
                    // 计算单元格是否放得下当前物品
                    int spareSpace = j - goods[goodsIndex].cost;
                    if(spareSpace < 0){
                        // 放不下 则放上一行同一列的值
                        calcArray[i][j] = preRowValue;
                    }else{
                        // 放得下，则需要判断上一行同一列的值跟当前商品 + 剩余空间的价值哪个大
                        int currentGoodsValue = goods[goodsIndex] .value;
                        currentGoodsValue += calcArray[preRow][spareSpace];
                        calcArray[i][j] = Math.max(preRowValue, currentGoodsValue);
                    }
                }
            }
            printRow(i, calcArray);
            System.out.println("-------------------------");
        }
        System.out.println("最终结果 : " + calcArray[goods.length][bagSize]);
        System.out.println(findWhich(goods.length, bagSize, calcArray, goods));
    }

    public static String findWhich(int axisX, int axisY, int[][] calcArray, Element[] goods){
        StringBuilder sb = new StringBuilder();
        if(axisX > 0){
            if(calcArray[axisX - 1][axisY] == calcArray[axisX][axisY]){
                // 如果上一行同一列的值跟当前值相等，说明本次物品没有放进去，则再向上寻找
                sb.append(findWhich(axisX - 1, axisY, calcArray, goods));
            }else{
                // 不相同说明当前物品放入了，添加进返回字符串中
                sb.append(goods[axisX - 1].name + ",");
                // 寻找除去当前物品 剩余容量的格子值
                sb.append(findWhich(axisX - 1, axisY - goods[axisX - 1].cost, calcArray, goods));
            }
        }
        return sb.toString();
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
