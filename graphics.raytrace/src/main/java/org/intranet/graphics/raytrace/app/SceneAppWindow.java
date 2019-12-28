package org.intranet.graphics.raytrace.app;

public final class SceneAppWindow
	extends AppWindow<SceneDocument>
{
	private static final long serialVersionUID = 1L;

	public SceneAppWindow(App<SceneDocument> app)
	{
		super(app);
	}

	@Override
	protected DocumentView<SceneDocument> createView()
	{ return new SceneDocumentView(); }
}