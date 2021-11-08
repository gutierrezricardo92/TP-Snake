package brains;

import java.util.LinkedList;
import java.util.List;

import edu.unlam.snake.brain.Brain;
import edu.unlam.snake.engine.Direction;
import edu.unlam.snake.engine.Point;

public class MyBrain extends Brain {

	private Direction direccion;
	private Point frutaCercana;
	private int posXdeFruta, posYdeFruta;
	private boolean puedoIrDerecha, puedoIrArriba, puedoIrIzquierda, puedoIrAbajo;

	public MyBrain() {
		super("MyBrain");
	}

	public Direction getDirection(Point head, Direction previous) {

		List<Point> fruits = info.getFruits();
		List<Point> snake = info.getSnake();
		List<List<Point>> enemies = info.getEnemies();
		List<Point> obstacles = info.getObstacles();

		obtenerDistanciaHastaFrutas(head, fruits);
		verificarDirecciones(head, snake, enemies, obstacles);
		avanzar(head, previous);

		return direccion;

	}

	private void obtenerDistanciaHastaFrutas(Point head, List<Point> fruits) {
				
		/* --- PARA "N" FRUTAS --- */
		frutaCercana = fruits.get(0);
		
		List<Integer> distancias = new LinkedList<Integer>();

		for (int i = 0; i < fruits.size(); i++) {
			Point fruta = fruits.get(i);
			distancias.add(Math.abs(fruta.getX() - head.getX()) + Math.abs(fruta.getY() - head.getY()));
		}

		int distanciaMenor = distancias.get(0);

		for (int i = 1; i < distancias.size(); i++) {

			if (distancias.get(i) < distanciaMenor) {
				distanciaMenor = distancias.get(i);
				frutaCercana = fruits.get(i);
			}

		}

		posXdeFruta = frutaCercana.getX();
		posYdeFruta = frutaCercana.getY();
		
		/* PARA TEST */
		System.out.println("La fruta cercana esta en: (" +posXdeFruta+ ", " +posYdeFruta+ ")");
//		mostrarLista(distancias);
		System.out.println("La distancia a la fruta cercana es: " +distanciaMenor);
		System.out.println("------------------------------------------------------------");

	}

	private void verificarDirecciones(Point head, List<Point> snake, List<List<Point>> enemies, List<Point> obstacles) {

		puedoIrIzquierda = true;
		puedoIrDerecha = true;
		puedoIrAbajo = true;
		puedoIrArriba = true;
		
		for (int i = 0; i < obstacles.size(); i++) {

			if (obstacles.get(i).getX() == head.getX() + 1 && obstacles.get(i).getY() == head.getY()) {
				puedoIrDerecha = false;
			}

			if (obstacles.get(i).getX() == head.getX() && obstacles.get(i).getY() == head.getY() + 1) {
				puedoIrArriba = false;
			}

			if (obstacles.get(i).getX() == head.getX() - 1 && obstacles.get(i).getY() == head.getY()) {
				puedoIrIzquierda = false;
			}

			if (obstacles.get(i).getX() == head.getX() && obstacles.get(i).getY() == head.getY() - 1) {
				puedoIrAbajo = false;
			}

		}

		for (int i = 0; i < snake.size(); i++) {

			if ((snake.get(i).getX() == head.getX() + 1 && snake.get(i).getY() == head.getY())
					|| (snake.get(i).getX() == head.getX() + 2 && snake.get(i).getY() == head.getY())) {
				puedoIrDerecha = false;
			}

			if ((snake.get(i).getX() == head.getX() && snake.get(i).getY() == head.getY() + 1)
					|| (snake.get(i).getX() == head.getX() && snake.get(i).getY() == head.getY() + 2)) {
				puedoIrArriba = false;
			}

			if ((snake.get(i).getX() == head.getX() - 1 && snake.get(i).getY() == head.getY())
					|| (snake.get(i).getX() == head.getX() - 2 && snake.get(i).getY() == head.getY())) {
				puedoIrIzquierda = false;
			}

			if ((snake.get(i).getX() == head.getX() && snake.get(i).getY() == head.getY() - 1)
					|| (snake.get(i).getX() == head.getX() && snake.get(i).getY() == head.getY() - 2)) {
				puedoIrAbajo = false;
			}

		}

		enemies.forEach(e -> {

			e.forEach(enemigo -> {

				if (enemigo.getX() == head.getX() + 1 && enemigo.getY() == head.getY()) {
					puedoIrDerecha = false;
				}

				if (enemigo.getX() == head.getX() && enemigo.getY() == head.getY() + 1) {
					puedoIrArriba = false;
				}

				if (enemigo.getX() == head.getX() - 1 && enemigo.getY() == head.getY()) {
					puedoIrIzquierda = false;
				}

				if (enemigo.getX() == head.getX() && enemigo.getY() == head.getY() - 1) {
					puedoIrAbajo = false;
				}

			});

		});

	}

	private void avanzar(Point head, Direction previous) {

		if ((posXdeFruta < head.getX()) && (previous != Direction.RIGHT) && puedoIrIzquierda == true) {
			direccion = Direction.LEFT;
		}

		if ((posXdeFruta > head.getX()) && (previous != Direction.LEFT) && puedoIrDerecha == true) {
			direccion = Direction.RIGHT;
		}

		if ((posYdeFruta < head.getY()) && (previous != Direction.UP) && puedoIrAbajo == true) {
			direccion = Direction.DOWN;
		}

		if ((posYdeFruta > head.getY()) && (previous != Direction.DOWN) && puedoIrArriba == true) {
			direccion = Direction.UP;
		}

	}

	public void mostrarLista(List<?> list) {
		list.forEach(System.out::println);
	}

}