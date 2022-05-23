package com.example.aircraftfight_android.game.application;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.aircraft.AbstractEnemy;
import com.example.aircraftfight_android.game.aircraft.BossEnemy;
import com.example.aircraftfight_android.game.aircraft.HeroAircraft;
import com.example.aircraftfight_android.game.basic.AbstractFlyingObject;
import com.example.aircraftfight_android.game.bullet.BaseBullet;
import com.example.aircraftfight_android.game.prop.AbstractProp;
import com.example.aircraftfight_android.game.prop.BombProp;
import com.example.aircraftfight_android.game.prop.BombTarget;
import com.example.aircraftfight_android.helper.DrawHeroHelper;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 * @author hitsz
 */
public abstract class Game extends SurfaceView implements SurfaceHolder.Callback{
    public final static String EASY = "EASY";
    public final static String NORMAL = "NORMAL";
    public final static String HARD = "HARD";

    protected GameOverCallback callback;

    protected boolean isPause = false;

    /**
     * 游戏背景
     */
    protected Bitmap backgroundImage;

    /**
     * 游戏模式，EASY、NORMAL、HARD之一
     */
    protected String mode;

    /**
     * 精英敌机出现概率
     */
    protected double eliteEnemyAriseProb;

    /**
     * 除boss外最大敌机数量
     */
    protected int enemyMaxNumber;

    /**
     * boss机出现阈值
     */
    protected int bossScoreThreshold;

    /**
     * 当前boss是否存在
     */
    protected boolean isBossExist = false;

    /**
     * 当前分数
     */
    protected int score = 0;

    protected final HeroAircraft heroAircraft = HeroAircraft.getInstance();
    protected final List<AbstractEnemy> enemyAircrafts = new LinkedList<>();
    protected final List<BaseBullet> heroBullets = new LinkedList<>();
    protected final List<BaseBullet> enemyBullets = new LinkedList<>();
    protected final List<AbstractProp> props = new LinkedList<>();

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int enemyAriseCycleDuration;
    protected int heroShootCycleDuration = 450;
    protected int enemyShootCycleDuration = 600;

    protected int backgroundSplitLength = 0;

    /**
     * Scheduled 线程池，用于定时任务调度
     * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
     * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
     */
    protected final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

    /**
     * 游戏刷新频率(ms)
     */
    protected int timeInterval = 18;

    /**
     * 计时器
     */
    protected int time = 0;

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;

    private DrawHeroHelper drawHeroHelper;
    private Context context;

    private void initView()
    {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        action();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public Game(Context context, GameOverCallback callback)
    {
        super(context);
        this.context = context;
        initView();

        heroAircraft.initHeroAircraft();
        this.callback = callback;
    }

    public void pauseGame(){
        isPause = true;
    }

    public void resumeGame(){
        isPause = false;
    }

    public boolean isPause() {
        return isPause;
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action()
    {
        backgroundImage = Bitmap.createScaledBitmap(backgroundImage, MainActivity.WIDTH, MainActivity.HEIGHT, false);
        MainActivity.musicHelper.startBackgroundMusic();

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            if(isPause) return;

            time += timeInterval;

            // 新建敌机（周期性执行控制频率）
            if (timeCountAndNewCycleJudge(enemyAriseCycleDuration)) {
                createEnemy();
            }

            // 各类飞机射击
            shootAction();

            // 各类移动
            moveAction();

            // 撞击检测
            crashCheckAction();

            // 敌机被击毁
            enemyDoneAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            paint();

            // 游戏结束检查
            gameOverCheck();

            // 随时间提高难度
            increaseDifficultyByTime();
        };

        // 以固定延迟时间进行执行, 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }

    protected boolean timeCountAndNewCycleJudge(int cycleDuration)
    {
        // 跨越到新的周期
        return time / cycleDuration > (time - timeInterval) / cycleDuration;
    }

    //***********************
    //      Action 各部分
    //***********************

    protected abstract void createEnemy();
    protected abstract void increaseDifficultyByTime();

    protected void moveAction()
    {
        // 子弹移动
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }

        // 飞机移动
        for (AbstractEnemy enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }

        // 道具移动
        for(AbstractProp prop : props){
            prop.forward();
        }
    }

    /**
     * 射击动作
     */
    protected void shootAction()
    {
        // 需要周期性执行以控制频率
        if(timeCountAndNewCycleJudge(enemyShootCycleDuration)){
            enemyShootAction();
        }

        if(timeCountAndNewCycleJudge(heroShootCycleDuration)){
            heroShootAction();
        }
    }

    /**
     * 英雄机射击
     */
    protected void heroShootAction()
    {
        heroBullets.addAll(heroAircraft.shoot());
    }

    /**
     * 敌机射击
     */
    protected void enemyShootAction()
    {
        for(AbstractEnemy enemyAircraft : enemyAircrafts)
        {
            List<BaseBullet> bullets = enemyAircraft.shoot();
            enemyBullets.addAll(bullets);
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */

    protected void crashCheckAction()
    {
        heroCrashEnemyCheck();

        enemyBulletCrashCheck();

        heroBulletCrashCheck();

        propCrashCheck();
    }

    /**
     * 检测英雄机与敌机相撞
     */
    protected void heroCrashEnemyCheck()
    {
        for (AbstractEnemy enemyAircraft : enemyAircrafts)
        {
            if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft))
            {
                MainActivity.musicHelper.playEnemyCrash();
                enemyAircraft.vanish();
                heroAircraft.decreaseHp(heroAircraft.getHp());
            }
        }
    }

    /**
     * 检测敌机子弹攻击英雄
     */
    protected void enemyBulletCrashCheck()
    {
        for(BaseBullet bullet : enemyBullets)
        {
            if(bullet.notValid() || heroAircraft.notValid()){
                continue;
            }

            if(heroAircraft.crash(bullet)){
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
    }

    /**
     * 检测英雄子弹攻击敌机
     */
    protected void heroBulletCrashCheck()
    {
        for (AbstractEnemy enemyAircraft : enemyAircrafts)
        {
            for (BaseBullet bullet : heroBullets)
            {
                if(enemyAircraft.notValid()){
                    break;
                }

                if(bullet.notValid()) {
                    continue;
                }

                // 敌机撞击到英雄机子弹
                if (enemyAircraft.crash(bullet))
                {
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                }
            }
        }
    }

    /**
     * 检测英雄机获得道具
     */
    protected void propCrashCheck()
    {
        for (AbstractProp prop : props)
        {
            if(prop.notValid() || heroAircraft.notValid()){
                continue;
            }

            if(heroAircraft.crash(prop))
            {
                // 获得炸弹道具，建立观察者和发布者（炸弹）的关联
                if(prop.getClass().getName().equals(BombProp.class.getName()))
                {
                    for(BombTarget enemy: enemyAircrafts) {
                        ((BombProp) prop).addTarget(enemy);
                    }
                    for(BaseBullet bullet: enemyBullets){
                        ((BombProp) prop).addTarget((BombTarget) bullet);
                    }
                }

                prop.activate();
                prop.vanish();
            }
        }
    }

    /**
     * 创建boss机
     * 抽象类中仅实现音效播放
     * 子类必须重写实现boss创建
     */
    protected void createBoss()
    {
        isBossExist = true;

        MainActivity.musicHelper.pauseBackgroundMusic();
        MainActivity.musicHelper.startBossMusic();
    }

    /**
     * 检测敌机被击毁
     */
    protected void enemyDoneAction()
    {
        boolean isCreateBoss = false;
        for (AbstractEnemy enemyAircraft : enemyAircrafts)
        {
            if(enemyAircraft.getHp() <= 0)
            {
                int lastScore = score;

                // 敌机掉落道具并加分
                props.addAll(enemyAircraft.dropProp());
                score += enemyAircraft.getScore();

                // 若摧毁了boss机，则boss存在标志置为false
                String enemyType = enemyAircraft.getClass().getName();
                if(enemyType.equals(BossEnemy.class.getName()))
                {
                    isBossExist = false;
                    MainActivity.musicHelper.stopBossMusic();
                    MainActivity.musicHelper.startBackgroundMusic();
                }

                // 分数超过阈值且不存在boss机，则创建boss机
                boolean isScoreReachTreshold = ((score / bossScoreThreshold) -
                        (lastScore / bossScoreThreshold) >= 1);
                if(isScoreReachTreshold && !isBossExist)
                {
                    isCreateBoss = true;
                    isBossExist = true;
                }
            }
        }

        if(isCreateBoss){
            createBoss();
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 无效的原因可能是撞击或者飞出边界
     */
    protected void postProcessAction()
    {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 检测游戏结束
     */
    protected void gameOverCheck()
    {
        if (heroAircraft.getHp() <= 0)
        {
            executorService.shutdown();
            MainActivity.musicHelper.pauseBackgroundMusic();
            MainActivity.musicHelper.stopBossMusic();

            MainActivity.musicHelper.playGameOver();
            callback.run(score, mode);
        }
    }


    /**
     * 英雄机控制
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if(x < 0 || x > MainActivity.WIDTH || y < 0 || y > MainActivity.HEIGHT){
            return false;
        }

        heroAircraft.setLocation(x, y);

        return true;
    }


    //***********************
    //      Paint 各部分
    //***********************
    protected void paint()
    {
        canvas = surfaceHolder.lockCanvas();
        this.drawHeroHelper = new DrawHeroHelper(timeInterval,canvas,context);

        // 背景循环移动
//        backgroundImage = Bitmap.createScaledBitmap(backgroundImage, canvas.getWidth(), canvas.getHeight(), false);

        Rect srcBottom = new Rect(0, 0, backgroundImage.getWidth(), backgroundImage.getHeight() - backgroundSplitLength);
        Rect dstBottom = new Rect(0, backgroundSplitLength, backgroundImage.getWidth(), backgroundImage.getHeight());
        canvas.drawBitmap(backgroundImage, srcBottom, dstBottom,null);

        Rect srcTop = new Rect(0, backgroundImage.getHeight() - backgroundSplitLength, backgroundImage.getWidth(), backgroundImage.getHeight());
        Rect dstTop = new Rect(0, 0, backgroundImage.getWidth(), backgroundSplitLength);
        canvas.drawBitmap(backgroundImage, srcTop, dstTop,null);

        backgroundSplitLength = (backgroundSplitLength + 2) % backgroundImage.getHeight();

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(canvas, enemyBullets);
        paintImageWithPositionRevised(canvas, heroBullets);

        paintImageWithPositionRevised(canvas, enemyAircrafts);

        // 绘制道具
        paintImageWithPositionRevised(canvas, props);

        // 绘制英雄
        drawHeroHelper.drawHero(time,heroAircraft.getLocationX(),heroAircraft.getLocationY());

        //绘制得分和生命值
        paintScoreAndLife(canvas);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    protected void paintImageWithPositionRevised(Canvas canvas, List<? extends AbstractFlyingObject> objects)
    {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            Bitmap image = object.getImage();
            canvas.drawBitmap(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    protected void paintScoreAndLife(Canvas canvas)
    {
        float x = 50;
        float y = 150;

        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText("Score: " + score, x, y, paint);
        canvas.drawText("Life: " + heroAircraft.getHp(), x, y + 100, paint);
    }
}