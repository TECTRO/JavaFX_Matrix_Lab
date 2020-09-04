public class MatrixNode
{
    public int value = 0;
    public String ColorHex = "";
    public String BackHex = "";
    public MatrixTypes elementType = MatrixTypes.defValue;
}

enum MatrixTypes
{
    mainDiagonal,
    sideDiagonal,
    midNode,
    minValue,
    maxValue,
    defValue
}
