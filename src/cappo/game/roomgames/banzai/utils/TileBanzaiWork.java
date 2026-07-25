package cappo.game.roomgames.banzai.utils;
import cappo.game.roomengine.itemInteractor.Interactor;

import cappo.engine.threadpools.RoomTask;
import cappo.game.collections.BaseItem;
import cappo.game.roomengine.entity.item.floor.GenericFloorItem;
import cappo.game.roomengine.gamemap.GameMapBase;
import cappo.game.roomengine.itemInteractor.Interactor.InteractorType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TileBanzaiWork
{
  public static void doWork(GenericFloorItem tile, int type, RoomTask room)
  {
    int data = tile.getIntData();
    int prefix = 3 * type;
    if ((data < prefix) || (data > prefix + 2))
    {
      data = prefix;
      tile.setIntData(data);
    }
    else
    {
      data = tile.incIntData(1);
    }
    if (data == prefix + 2)
    {
      if (type == 1)
      {
        room.ScorePoints_R += 1;
        for (GenericFloorItem item : room.roomGamesScorersRED)
        {
          item.setIntData(room.ScorePoints_R);
          room.floorItemUpdateNeeded(item);
        }
      }
      else if (type == 2)
      {
        room.ScorePoints_G += 1;
        for (GenericFloorItem item : room.roomGamesScorersGREEN)
        {
          item.setIntData(room.ScorePoints_G);
          room.floorItemUpdateNeeded(item);
        }
      }
      else if (type == 3)
      {
        room.ScorePoints_B += 1;
        for (GenericFloorItem item : room.roomGamesScorersBLUE)
        {
          item.setIntData(room.ScorePoints_B);
          room.floorItemUpdateNeeded(item);
        }
      }
      else if (type == 4)
      {
        room.ScorePoints_Y += 1;
        for (GenericFloorItem item : room.roomGamesScorersYELLOW)
        {
          item.setIntData(room.ScorePoints_Y);
          room.floorItemUpdateNeeded(item);
        }
      }
      List<GenericFloorItem> combo = findCombo(tile, data, tile.getX(), tile.getY(), 0, 0, -1, 4);
      if (combo != null) {
        fillCombo(tile, type, room, combo);
      }
    }
    room.floorItemUpdateNeeded(tile);
  }
  
  private static List<GenericFloorItem> findCombo(GenericFloorItem tile, int find, int X, int Y, int xCan, int yCan, int curRot, int turn)
  {
    boolean[] moves = new boolean[4];
    if (xCan == -1)
    {
      moves[0] = true;
    }
    else if (xCan == 1)
    {
      moves[2] = true;
    }
    else if (xCan == 0)
    {
      moves[0] = true;
      moves[2] = true;
    }
    if (yCan == -1)
    {
      moves[1] = true;
    }
    else if (yCan == 1)
    {
      moves[3] = true;
    }
    else if (yCan == 0)
    {
      moves[1] = true;
      moves[3] = true;
    }
    if (((xCan != 0) || (yCan != 0)) && (tile.getX() == X) && (tile.getY() == Y)) {
      return new ArrayList();
    }
    for (int i = 0; i < 4; i++) {
      if (moves[i])
      {
        int x;
        int y;
        if (i == 0) {
          x = X + 1;
          y = Y;
        } else if (i == 1) {
          x = X;
          y = Y + 1;
        } else if (i == 2) {
          x = X - 1;
          y = Y;
        } else {
          x = X;
          y = Y - 1;
        }
        RoomTask room = tile.getRoom();
        
        int nextXY = x + y * room.model.widthX;
        if ((x < room.model.widthX) && (y < room.model.heightY))
        {
          GenericFloorItem top = (GenericFloorItem)room.topFloorItems.get(Integer.valueOf(nextXY));
          if ((top != null) && 
            (top.getIntData() == find)) {
            if ((curRot != i) && (curRot != -1))
            {
              if (turn != 0)
              {
                List<GenericFloorItem> found = null;
                if (i == 0) {
                  found = findCombo(tile, find, x, y, -1, yCan * -1, i, turn - 1);
                } else if (i == 1) {
                  found = findCombo(tile, find, x, y, xCan * -1, -1, i, turn - 1);
                } else if (i == 2) {
                  found = findCombo(tile, find, x, y, 1, yCan * -1, i, turn - 1);
                } else if (i == 3) {
                  found = findCombo(tile, find, x, y, xCan * -1, 1, i, turn - 1);
                }
                if (found != null)
                {
                  found.add(top);
                  return found;
                }
              }
            }
            else
            {
              List<GenericFloorItem> found = null;
              if (i == 0) {
                found = findCombo(tile, find, x, y, -1, yCan, i, turn);
              } else if (i == 1) {
                found = findCombo(tile, find, x, y, xCan, -1, i, turn);
              } else if (i == 2) {
                found = findCombo(tile, find, x, y, 1, yCan, i, turn);
              } else if (i == 3) {
                found = findCombo(tile, find, x, y, xCan, 1, i, turn);
              }
              if (found != null)
              {
                found.add(top);
                return found;
              }
            }
          }
        }
      }
    }
    return null;
  }
  
  public static void fillCombo(GenericFloorItem tile, int type, RoomTask room, List<GenericFloorItem> combo)
  {
    int startX = 255;
    int startY = 255;
    int endX = -1;
    int endY = -1;
    for (GenericFloorItem item : combo)
    {
      if (item.getX() < startX) {
        startX = item.getX();
      }
      if (item.getY() < startY) {
        startY = item.getY();
      }
      if (item.getX() > endX) {
        endX = item.getX();
      }
      if (item.getY() > endY) {
        endY = item.getY();
      }
    }
    startX++;
    startY++;
    
    int score = 0;
    GenericFloorItem top;
    for (; startX < endX; startX++) {
      for (int y = startY; y < endY; y++)
      {
        top = (GenericFloorItem)room.topFloorItems.get(Integer.valueOf(startX + y * room.model.widthX));
        if ((top != null) && 
          (top.baseItem.interactorType == Interactor.InteractorType.banzaifloor) && (top.extraData != tile.extraData))
        {
          top.setIntData(tile.getIntData());
          room.floorItemUpdateNeeded(top);
          score++;
        }
      }
    }
    if (type == 1)
    {
      room.ScorePoints_R += score;
      for (GenericFloorItem item : room.roomGamesScorersRED)
      {
        item.setIntData(room.ScorePoints_R);
        room.floorItemUpdateNeeded(item);
      }
    }
    else if (type == 2)
    {
      room.ScorePoints_G += score;
      for (GenericFloorItem item : room.roomGamesScorersGREEN)
      {
        item.setIntData(room.ScorePoints_R);
        room.floorItemUpdateNeeded(item);
      }
    }
    else if (type == 3)
    {
      room.ScorePoints_B += score;
      for (GenericFloorItem item : room.roomGamesScorersBLUE)
      {
        item.setIntData(room.ScorePoints_R);
        room.floorItemUpdateNeeded(item);
      }
    }
    else if (type == 4)
    {
      room.ScorePoints_Y += score;
      for (GenericFloorItem item : room.roomGamesScorersYELLOW)
      {
        item.setIntData(room.ScorePoints_R);
        room.floorItemUpdateNeeded(item);
      }
    }
  }
}
