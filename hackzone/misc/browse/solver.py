import websocket


ws = websocket.WebSocket()


uri="ws://curl-terminal-task1-yuri.terminal.thehotpirate.com/websocket"
origin="http://curl-terminal-task1-yuri.terminal.thehotpirate.com"
async with websockets.client.connect(uri, origin=origin) as websocket:
	msg = dict(op="info")
	await websocket.send(json.dumps(msg))
	response = await wesbsocket.recv()
	print response
#ws.connect("ws://curl-terminal-task1-yuri.terminal.thehotpirate.com/websocket",subprotocols=["tty"])



