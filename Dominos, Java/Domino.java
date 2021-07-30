public class Domino {
    private int _face1;

    private int _face2;
    
    private boolean _isTurned;

    private boolean _topPiece;

    private Domino upperDomino;
    private Domino lowerDomino;

    private int worth;

    public Domino(int face1, int face2){
        _face1 = face1;
        _face2 = face2;

        if(face1 + face2 == 0) worth = 10;
        else worth = face1 + face2;

        _isTurned = false;
        _topPiece = false;
        upperDomino = null;
        lowerDomino = null;
    }

    public boolean is_topPiece() {
        return _topPiece;
    }

    public boolean isTurned(){return _isTurned;}

    public boolean equalFaces(){
        return _face1 == _face2;
    }

    public void topDomino(Domino domino) {
        upperDomino = domino;
    }

    public void bottomDomino(Domino domino) {
        lowerDomino = domino;
    }

    public Domino nextDomino(){
        return lowerDomino;
    }
    public void turn(){
        _isTurned = true;
    }

    public int worth(){return worth;}

    public int isFree(Domino domino,int isTop){ //return 0 if not turned, 1 if it is, -1 if not compatible, if is top = 0 is top else is bottom
        if(isTop == 1){ //is bottom domino
            if(this.isTurned()){
                if(domino.getFace1() == this.getFace1()) return 0;
                else if(domino.getFace2() == this.getFace1()) return 1;
                else return -1;
            }
            else{
                if(domino.getFace1() == this.getFace2()) return 0;
                else if(domino.getFace2() == this.getFace2()) return 1;
                else return -1;
            }
        }
        else{ //top domino
            if(this.isTurned()){
                if(domino.getFace2() == this.getFace2()) return 0;
                else if(domino.getFace1() == this.getFace2()) return 1;
                else return -1;
            }

            else{
                if(domino.getFace2() == this.getFace1()) return 0;
                else if(domino.getFace1() == this.getFace1()) return 1;
                else return -1;

            }
        }
    }

    public boolean isThatDomino(Domino domino) {//if this domino was the top domino it will return true if has now a top domino
        return domino.equals(upperDomino);
    }

    public int getFace1(){return _face1;}

    public int getFace2(){return _face2;}

    public void reset(){
        upperDomino = null;
        lowerDomino = null;
        _topPiece = false;
        _isTurned = false;
    }

    @Override
    public String toString(){
        if(equalFaces()) return "|" + _face1 +  "|" + _face2 + "|";
        else if(_isTurned)return "|" + _face2 + "|" + "\n---\n" + "|" + _face1 + "|";
        else return "|" + _face1 + "|" + "\n---\n" + "|" + _face2 + "|";
    }
}
