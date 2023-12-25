package com.mfa.gamesbetter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;

public class GamesBetter extends ApplicationAdapter {

	SpriteBatch batch;//SpriteBatch kullanarak kuş,düşman ve arka plan çizilir.

	Texture background;

	Texture bird;

	Texture bee1;

	Texture bee2;

	Texture bee3;

	float birdX=0;

	float birdY=0;

	int gameState=0; //oyun başlamadıysa gameState=0, oyun başladıysa gameState=1

	float velocity=7; //hız

	float gravity=0.1f;//yerçekimi

	float enemyVelocity = 7;

	Random random;

	int score = 0;

	int scoredEnemy = 0;

	BitmapFont font;

	BitmapFont font2;

	BitmapFont font3;

	Circle birdCircle;

	ShapeRenderer shapeRenderer;



	int numberOfEnemySet = 4;
	float [] enemyX = new float[numberOfEnemySet];

	float [] enemyOffSet = new float[numberOfEnemySet];
	float [] enemyOffSet2 = new float[numberOfEnemySet];
	float [] enemyOffSet3 = new float[numberOfEnemySet];

	float distance = 2;//mesafe

	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;




	@Override
	public void create ()  { //Oyun başladığında ne olacaksa create'in içine yazılır.

		batch = new SpriteBatch();
		background = new Texture("background_mountain.png");//Arka plan çizimi başlatıldı.
		bird = new Texture("bird_blue.png");
		bee1 = new Texture("ufo.png");
		bee2 = new Texture("ufo.png");
		bee3 = new Texture("ufo.png");



		distance = Gdx.graphics.getWidth()/2;

		random = new Random();

		birdX = Gdx.graphics.getWidth()/2-bird.getWidth()/2;
		birdY = Gdx.graphics.getHeight()/2;

		shapeRenderer = new ShapeRenderer();


		birdCircle = new Circle();

		enemyCircles = new Circle[numberOfEnemySet];
		enemyCircles2 = new Circle[numberOfEnemySet];
		enemyCircles3 = new Circle[numberOfEnemySet];



		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4); // Fontun daha büyük olması için kullanıldı.

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(4);

		font3=new BitmapFont();
		font3.setColor(Color.WHITE);
		font3.getData().setScale(4);






		for(int i=0;i<numberOfEnemySet;i++) {

			enemyOffSet[i] = (random.nextFloat() -0.55f ) * (Gdx.graphics.getHeight() - 300);//random y ekseni oluşturulacak.
			enemyOffSet2[i] = (random.nextFloat() -0.55f) * (Gdx.graphics.getHeight() -300 );//random.nextFloat== 0 ile 1 arasında değer verecek.
			enemyOffSet3[i] = (random.nextFloat() -0.55f) * (Gdx.graphics.getHeight() - 300 );



			enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth()/2 + i * distance;

			enemyCircles [i] = new Circle();
			enemyCircles2 [i] = new Circle();
			enemyCircles3 [i] = new Circle();
		}

	}

	@Override
	public void render () { //Oyun devam ettiği sürece devamlı çağırılan metot render metodu

		batch.begin();

		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState == 1) {

			if(enemyX[scoredEnemy] < Gdx.graphics.getWidth()/2-bird.getWidth()/2) {
				score++; // kuş düşmanı geçtiyse skoru 1 arttır.

				if(scoredEnemy<numberOfEnemySet-1) {
					scoredEnemy++;
				}
				else {
					scoredEnemy = 0;
				}
			}


			if (Gdx.input.justTouched()) {
				velocity = -8; //Kuş uçuruldu.
			}

			for (int i = 0; i < numberOfEnemySet; i++) {

				if (enemyX[i] < Gdx.graphics.getWidth() / 15) {
					enemyX[i] = enemyX[i] + numberOfEnemySet * distance;

					enemyOffSet[i] = (random.nextFloat() -0.5f ) * (Gdx.graphics.getHeight() - 200)  ;
					enemyOffSet2[i] = (random.nextFloat() -0.5f ) * (Gdx.graphics.getHeight() -200)  ;
					enemyOffSet3[i] = (random.nextFloat() -0.5f ) * (Gdx.graphics.getHeight() -200)  ;




				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				batch.draw(bee1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(bee2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(bee3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);


				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/ 30 ,Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight()/2+enemyOffSet2[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,Gdx.graphics.getHeight()/2+enemyOffSet3[i]+Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);


			}


			if (birdY > 0) { //yerçekimi oluşumu kuş sonsuza gitmiyor.
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}


			else { //Kuş y'nin altına aşağı düşerse oyun biter.
				gameState=2;
			}

		}else  if (gameState==0){ //gameState=0 ise oyun başlamadı 1 ise başladı 2 ise bitti.
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState==2) {

			font2.draw(batch,"Oyun Bitti ! Tekrar Oynamak Icin Tiklayiniz !",50 ,Gdx.graphics.getHeight() / 2);

			if(Gdx.input.justTouched()) {
				gameState = 1; //oyun bitip bir yere tıklandığında oyun yeniden başlayacağı için kuşu aynı y eksenine almak gerekir.

				birdY = Gdx.graphics.getHeight()/2;

				for(int i=0;i<numberOfEnemySet;i++) {

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200)  ;//random y ekseni oluşturulacak.
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200 )  ;//random.nextFloat== 0 ile 1 arasında değer verecek.
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200)  ;



					enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth()/2 + i * distance;

					enemyCircles [i] = new Circle();
					enemyCircles2 [i] = new Circle();
					enemyCircles3 [i] = new Circle();
				}

				velocity = 0; //oyun bittiğinde kuş çok yükseklerdeyse sonunda hızı sıfırlayıp oyun yeniden başlayacağı için velocity=0
				scoredEnemy = 0;
				score = 0;
			}
		}



		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);

		font.draw(batch,String.valueOf(score),100,200);



		batch.end();

		birdCircle.set(birdX + (Gdx.graphics.getWidth() / 30),birdY + (Gdx.graphics.getHeight() / 20),Gdx.graphics.getWidth() /30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);


		for(int i=0;i<numberOfEnemySet;i++) {


			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 22 , Gdx.graphics.getHeight() / 2 + enemyOffSet[i]+ Gdx.graphics.getHeight() / 22 ,Gdx.graphics.getWidth()/22);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 22 , Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 22 , Gdx.graphics.getWidth() /22);
			//shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 22 , Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 22 ,Gdx.graphics.getWidth() /22);


			if(Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])) {
				gameState=2;
			}


		}
		//shapeRenderer.end();
	}

	@Override
	public void dispose () {

	}
}
