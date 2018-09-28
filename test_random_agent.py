import gym
import gym_gvgai
import argparse

all_envs = set([env.id.split("-")[1] for env in gym.envs.registry.all() if env.id.startswith('gvgai')])
print(all_envs)

parser = argparse.ArgumentParser(description=None)
parser.add_argument('env_id', default='aliens', help='Select the environment to run')
parser.add_argument('level', default=0, help='Select the environment to run')
args = parser.parse_args()
env_id = "gvgai-{}-lvl{}-v0".format(args.env_id, args.level)

env = gym.make(env_id)
env.reset()

score = 0
for i in range(2000):
    action_id = env.action_space.sample()
    state, reward, isOver, info = env.step(action_id)
    env.render()
    score += reward
    print("Action " + str(action_id) + " played at game tick " + str(i+1) + ", reward=" + str(reward) + ", new score=" + str(score))
    if isOver:
        print("Game over at game tick " + str(i+1) + " with player " + info['winner'])
        break
