package com.cherish.livewallpaper;

import android.graphics.Matrix;

/**
 * Created by lijianglong on 2018/2/9.
 */

public class ScaleManager {

	private Size mViewSize;
	private Size mVideoSize;

	public ScaleManager(Size viewSize, Size videoSize) {
		mViewSize = viewSize;
		mVideoSize = videoSize;
	}

	public Matrix getScaleMatrix(ScalableType scalableType) {
		switch (scalableType) {
			case NONE:
				return getNoScale();

			case FIT_XY:
				return fitXY();
			case FIT_CENTER:
				return fitCenter();
			case FIT_START:
				return fitStart();
			case FIT_END:
				return fitEnd();

			case LEFT_TOP:
				return getOriginalScale(ScaleType.LEFT_TOP);
			case LEFT_CENTER:
				return getOriginalScale(ScaleType.LEFT_CENTER);
			case LEFT_BOTTOM:
				return getOriginalScale(ScaleType.LEFT_BOTTOM);
			case CENTER_TOP:
				return getOriginalScale(ScaleType.CENTER_TOP);
			case CENTER:
				return getOriginalScale(ScaleType.CENTER);
			case CENTER_BOTTOM:
				return getOriginalScale(ScaleType.CENTER_BOTTOM);
			case RIGHT_TOP:
				return getOriginalScale(ScaleType.RIGHT_TOP);
			case RIGHT_CENTER:
				return getOriginalScale(ScaleType.RIGHT_CENTER);
			case RIGHT_BOTTOM:
				return getOriginalScale(ScaleType.RIGHT_BOTTOM);

			case LEFT_TOP_CROP:
				return getCropScale(ScaleType.LEFT_TOP);
			case LEFT_CENTER_CROP:
				return getCropScale(ScaleType.LEFT_CENTER);
			case LEFT_BOTTOM_CROP:
				return getCropScale(ScaleType.LEFT_BOTTOM);
			case CENTER_TOP_CROP:
				return getCropScale(ScaleType.CENTER_TOP);
			case CENTER_CROP:
				return getCropScale(ScaleType.CENTER);
			case CENTER_BOTTOM_CROP:
				return getCropScale(ScaleType.CENTER_BOTTOM);
			case RIGHT_TOP_CROP:
				return getCropScale(ScaleType.RIGHT_TOP);
			case RIGHT_CENTER_CROP:
				return getCropScale(ScaleType.RIGHT_CENTER);
			case RIGHT_BOTTOM_CROP:
				return getCropScale(ScaleType.RIGHT_BOTTOM);

			case START_INSIDE:
				return startInside();
			case CENTER_INSIDE:
				return centerInside();
			case END_INSIDE:
				return endInside();

			default:
				return null;
		}
	}

	private Matrix getMatrix(float sx, float sy, float px, float py) {
		Matrix matrix = new Matrix();
		matrix.setScale(sx, sy, px, py);
		return matrix;
	}

	private Matrix getMatrix(float sx, float sy, ScaleType pivotPoint) {
		switch (pivotPoint) {
			case LEFT_TOP:
				return getMatrix(sx, sy, 0, 0);
			case LEFT_CENTER:
				return getMatrix(sx, sy, 0, mViewSize.getHeight() / 2f);
			case LEFT_BOTTOM:
				return getMatrix(sx, sy, 0, mViewSize.getHeight());
			case CENTER_TOP:
				return getMatrix(sx, sy, mViewSize.getWidth() / 2f, 0);
			case CENTER:
				return getMatrix(sx, sy, mViewSize.getWidth() / 2f, mViewSize.getHeight() / 2f);
			case CENTER_BOTTOM:
				return getMatrix(sx, sy, mViewSize.getWidth() / 2f, mViewSize.getHeight());
			case RIGHT_TOP:
				return getMatrix(sx, sy, mViewSize.getWidth(), 0);
			case RIGHT_CENTER:
				return getMatrix(sx, sy, mViewSize.getWidth(), mViewSize.getHeight() / 2f);
			case RIGHT_BOTTOM:
				return getMatrix(sx, sy, mViewSize.getWidth(), mViewSize.getHeight());
			default:
				throw new IllegalArgumentException("Illegal PivotPoint");
		}
	}

	private Matrix getNoScale() {
		float sx = mVideoSize.getWidth() / (float) mViewSize.getWidth();
		float sy = mVideoSize.getHeight() / (float) mViewSize.getHeight();
		return getMatrix(sx, sy, ScaleType.LEFT_TOP);
	}

	private Matrix getFitScale(ScaleType pivotPoint) {
		float sx = (float) mViewSize.getWidth() / mVideoSize.getWidth();
		float sy = (float) mViewSize.getHeight() / mVideoSize.getHeight();
		float minScale = Math.min(sx, sy);
		sx = minScale / sx;
		sy = minScale / sy;
		return getMatrix(sx, sy, pivotPoint);
	}

	private Matrix fitXY() {
		return getMatrix(1, 1, ScaleType.LEFT_TOP);
	}

	private Matrix fitStart() {
		return getFitScale(ScaleType.LEFT_TOP);
	}

	private Matrix fitCenter() {
		return getFitScale(ScaleType.CENTER);
	}

	private Matrix fitEnd() {
		return getFitScale(ScaleType.RIGHT_BOTTOM);
	}

	private Matrix getOriginalScale(ScaleType pivotPoint) {
		float sx = mVideoSize.getWidth() / (float) mViewSize.getWidth();
		float sy = mVideoSize.getHeight() / (float) mViewSize.getHeight();
		return getMatrix(sx, sy, pivotPoint);
	}

	private Matrix getCropScale(ScaleType pivotPoint) {
		float sx = (float) mViewSize.getWidth() / mVideoSize.getWidth();
		float sy = (float) mViewSize.getHeight() / mVideoSize.getHeight();
		float maxScale = Math.max(sx, sy);
		sx = maxScale / sx;
		sy = maxScale / sy;
		return getMatrix(sx, sy, pivotPoint);
	}

	private Matrix startInside() {
		if (mVideoSize.getHeight() <= mViewSize.getWidth()
				&& mVideoSize.getHeight() <= mViewSize.getHeight()) {
			// video is smaller than view size
			return getOriginalScale(ScaleType.LEFT_TOP);
		} else {
			// either of width or height of the video is larger than view size
			return fitStart();
		}
	}

	private Matrix centerInside() {
		if (mVideoSize.getHeight() <= mViewSize.getWidth()
				&& mVideoSize.getHeight() <= mViewSize.getHeight()) {
			// video is smaller than view size
			return getOriginalScale(ScaleType.CENTER);
		} else {
			// either of width or height of the video is larger than view size
			return fitCenter();
		}
	}

	private Matrix endInside() {
		if (mVideoSize.getHeight() <= mViewSize.getWidth()
				&& mVideoSize.getHeight() <= mViewSize.getHeight()) {
			// video is smaller than view size
			return getOriginalScale(ScaleType.RIGHT_BOTTOM);
		} else {
			// either of width or height of the video is larger than view size
			return fitEnd();
		}
	}

	public static class Size {

		private int mWidth;
		private int mHeight;

		public Size(int width, int height) {
			mWidth = width;
			mHeight = height;
		}

		public int getWidth() {
			return mWidth;
		}

		public int getHeight() {
			return mHeight;
		}
	}
}